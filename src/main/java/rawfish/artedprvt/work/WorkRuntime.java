package rawfish.artedprvt.work;

import rawfish.artedprvt.std.cli.CompleteInterface;
import rawfish.artedprvt.std.cli.FormatInterface;
import rawfish.artedprvt.std.cli.InfoHandler;
import rawfish.artedprvt.std.cli.InfoInterface;
import rawfish.artedprvt.std.cli.ProcessInterface;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class WorkRuntime {
    private ProjectSystem projectSystem;//close
    private ClassLoader classLoader;//close
    private Map<String, PhaseData> phaseDataMap = new HashMap<>();
    private Map<String, LifecycleData> lifecycleDataMap = new HashMap<>();
    private Map<String, GoalData> goalDataMap = new HashMap<>();
    private Map<String, CommandData> commandDataMap = new HashMap<>();


    private Map<String, PhaseHandle> phaseHandleMap = new HashMap<>();//close
    private Map<String, List<PhaseHandle>> lifecyclePhaseHandleMap = new HashMap<>();//close
    private Map<String, GoalHandle> goalHandleMap = new HashMap<>();//close
    private Map<String, CommandHandle> commandHandleMap = new HashMap<>();//close

    private List<String> phaseRefNameList = new ArrayList<>();
    private List<String> lifecycleRefNameList = new ArrayList<>();
    private List<String> goalRefNameList = new ArrayList<>();
    private List<String> commandRefNameList = new ArrayList<>();

    private Map<String, String> phaseLifecycleMap = new HashMap<>();
    private Map<String, String> goalLifecycleMap = new HashMap<>();

    public WorkRuntime(
            ProjectSystem projectSystem,
            ClassLoader classLoader,
            List<PhaseData> phaseDataList,
            List<LifecycleData> lifecycleDataList,
            List<GoalData> goalDataList,
            List<CommandData> commandDataList) {
        this.projectSystem=projectSystem;
        this.classLoader = classLoader;
        phaseDataList.forEach(v -> phaseDataMap.put(v.phaseName, v));
        lifecycleDataList.forEach(v -> lifecycleDataMap.put(v.lifecycleName, v));
        goalDataList.forEach(v -> goalDataMap.put(v.goalName, v));
        commandDataList.forEach(v -> commandDataMap.put(v.commandName, v));
        try {
            init();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private void init() throws Exception {
        for (LifecycleData lifecycleData : lifecycleDataMap.values()) {
            List<PhaseHandle> phaseHandleList = new LinkedList<>();
            for (String phaseName : lifecycleData.phaseNames) {
                PhaseData phaseData = phaseDataMap.get(phaseName);
                Class phaseClass = classLoader.loadClass(phaseData.className);
                String phaseHandleName = lifecycleData.lifecycleName + "/" + phaseName;
                String phaseRefName = phaseName;

                Constructor phaseConstructor = phaseClass.getConstructor();
                phaseConstructor.setAccessible(true);
                Object phaseObject = phaseConstructor.newInstance();

                List<GoalHandle> goalHandleList = new ArrayList<>();
                for (GoalData goalData : goalDataMap.values()) {
                    if (goalData.phaseName.equals(phaseName)) {
                        Class goalClass = classLoader.loadClass(goalData.className);
                        String goalHandleName = phaseHandleName + "/" + goalData.goalName;
                        String goalRefName = phaseRefName + ":" + goalData.goalName;
                        Constructor goalConstructor = goalClass.getConstructor();
                        goalConstructor.setAccessible(true);
                        Object goalObject = goalConstructor.newInstance();
                        InfoHandler infoHandler=getInfoHandler(goalObject);
                        if(infoHandler==null){
                            infoHandler=getInfoHandler(phaseObject);
                        }
                        GoalHandle goalHandle = new GoalHandle(
                                goalHandleName,
                                getProcessInterface(goalObject),
                                infoHandler);

                        goalHandleList.add(goalHandle);
                        goalHandleMap.put(goalRefName, goalHandle);
                        goalRefNameList.add(goalRefName);

                        goalLifecycleMap.put(goalRefName, goalData.goalName);
                    }
                }

                PhaseHandle phaseHandle = new PhaseHandle(
                        phaseHandleName,
                        goalHandleList,
                        getInfoHandler(phaseObject));

                phaseHandleList.add(phaseHandle);
                phaseHandleMap.put(phaseRefName, phaseHandle);
                phaseRefNameList.add(phaseRefName);

                phaseLifecycleMap.put(phaseRefName, lifecycleData.lifecycleName);
            }

            lifecyclePhaseHandleMap.put(lifecycleData.lifecycleName, phaseHandleList);
            lifecycleRefNameList.add(lifecycleData.lifecycleName);
        }

        for (GoalData goalData : goalDataMap.values()) {
            if (goalData.phaseName.equals("null;")) {
                Class goalClass = classLoader.loadClass(goalData.className);
                String goalHandleName = "/" + goalData.goalName;
                String goalRefName = ":" + goalData.goalName;
                Constructor goalConstructor = goalClass.getConstructor();
                goalConstructor.setAccessible(true);
                Object goalObject = goalConstructor.newInstance();
                GoalHandle goalHandle = new GoalHandle(
                        goalHandleName,
                        getProcessInterface(goalObject),
                        getInfoHandler(goalObject));

                goalHandleMap.put(goalRefName, goalHandle);
                goalRefNameList.add(goalRefName);
            }
        }

        for (CommandData commandData : commandDataMap.values()) {
            Class commandClass = classLoader.loadClass(commandData.className);
            String commandHandleName = commandData.commandName;
            String commandRefName = commandData.commandName;
            Constructor commandConstructor = commandClass.getConstructor();
            commandConstructor.setAccessible(true);
            Object commandObject = commandConstructor.newInstance();
            CommandHandle commandHandle = new CommandHandle(
                    commandHandleName,
                    getProcessInterface(commandObject),
                    getCompleteInterface(commandObject),
                    getFormatInterface(commandObject),
                    getInfoInterface(commandObject));

            commandHandleMap.put(commandRefName, commandHandle);
            commandRefNameList.add(commandRefName);
        }

        Class apClass=classLoader.loadClass("rawfish.artedprvt.work._0.ap");
        ProjectAccess projectAccess=(ProjectAccess)apClass.newInstance();
        projectAccess.main(projectSystem);
    }

    private ProcessInterface getProcessInterface(Object object) {
        if (ProcessInterface.class.isAssignableFrom(object.getClass())) {
            return (ProcessInterface) object;
        }
        return null;
    }

    private CompleteInterface getCompleteInterface(Object object) {
        if (CompleteInterface.class.isAssignableFrom(object.getClass())) {
            return (CompleteInterface) object;
        }
        return null;
    }

    private FormatInterface getFormatInterface(Object object) {
        if (FormatInterface.class.isAssignableFrom(object.getClass())) {
            return (FormatInterface) object;
        }
        return null;
    }

    private InfoInterface getInfoInterface(Object object) {
        if (InfoInterface.class.isAssignableFrom(object.getClass())) {
            return (InfoInterface) object;
        }
        return null;
    }

    private InfoHandler getInfoHandler(Object object) {
        if (InfoHandler.class.isAssignableFrom(object.getClass())) {
            return (InfoHandler) object;
        }
        return null;
    }

    /**
     * 获取所有阶段名
     *
     * @return phase
     */
    public List<String> getPhases() {
        return new ArrayList<>(phaseRefNameList);
    }

    /**
     * 获取所有生命周期名
     *
     * @return lifecycle
     */
    public List<String> getLifecycle() {
        return new ArrayList<>(lifecycleRefNameList);
    }

    /**
     * 获取所有目标名
     *
     * @return phase:goal
     */
    public List<String> getGoals() {
        return new ArrayList<>(goalRefNameList);
    }

    /**
     * 获取阶段的生命周期
     *
     * @param phaseName phase
     * @return lifecycle
     */
    public String getLifecycleOfPhase(String phaseName) {
        return phaseLifecycleMap.get(phaseName);
    }

    /**
     * 获取目标的生命周期
     *
     * @param goalName goal
     * @return lifecycle
     */
    public String getLifecycleOfGoal(String goalName) {
        return goalLifecycleMap.get(goalName);
    }

    /**
     * 获取所有命令名
     *
     * @return command
     */
    public List<String> getCommands() {
        return new ArrayList<>(commandRefNameList);
    }

    /**
     * 获取阶段处理
     *
     * @param name 阶段名
     * @return 阶段处理列表 所在生命周期从第一个阶段到此阶段
     */
    public List<PhaseHandle> getPhaseHandles(String name) {
        LifecycleData lifecycleData = lifecycleDataMap.get(phaseLifecycleMap.get(name));
        List<PhaseHandle> phaseHandleList = lifecyclePhaseHandleMap.get(lifecycleData.lifecycleName);
        List<PhaseHandle> result = new LinkedList<>();

        for (int i = 0; i < lifecycleData.phaseNames.length; i++) {
            result.add(phaseHandleList.get(i));
            if (lifecycleData.phaseNames[i].equals(name)) {
                break;
            }
        }
        return result;
    }

    /**
     * 获取阶段处理
     *
     * @param name 阶段名
     * @return
     */
    public PhaseHandle getPhaseHandle(String name) {
        return phaseHandleMap.get(name);
    }

    /**
     * 获取目标处理
     *
     * @param name 目标名
     * @return
     */
    public GoalHandle getGoalHandle(String name) {
        return goalHandleMap.get(name);
    }

    /**
     * 返回命令处理
     *
     * @param name 命令名
     * @return
     */
    public CommandHandle getCommandHandle(String name) {
        return commandHandleMap.get(name);
    }

    public void close() {
        projectSystem=null;
        classLoader = null;
        phaseHandleMap = null;
        lifecyclePhaseHandleMap = null;
        goalHandleMap = null;
        commandHandleMap = null;
    }
}
