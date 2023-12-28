package rawfish.artedprvt.work;

import rawfish.artedprvt.std.cli.CompleteInterface;
import rawfish.artedprvt.std.cli.FormatInterface;
import rawfish.artedprvt.std.cli.InfoInterface;
import rawfish.artedprvt.std.cli.ProcessInterface;

import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class WorkRuntime {
    private ClassLoader classLoader;//close
    private Map<String, PhaseData> phaseDataMap = new HashMap<>();
    private Map<String, LifecycleData> lifecycleDataMap = new HashMap<>();
    private Map<String, GoalData> goalDataMap = new HashMap<>();


    private Map<String, PhaseHandle> phaseHandleMap = new HashMap<>();//close
    private Map<String, List<PhaseHandle>> lifecyclePhaseHandleMap = new HashMap<>();//close
    private Map<String, GoalHandle> goalHandleMap = new HashMap<>();//close

    private List<String> phaseRefNameList = new ArrayList<>();
    private List<String> lifecycleRefNameList = new ArrayList<>();
    private List<String> goalRefNameList = new ArrayList<>();

    private Map<String, String> phaseLifecycleMap = new HashMap<>();

    public WorkRuntime(
            ClassLoader classLoader,
            List<PhaseData> phaseDataList,
            List<LifecycleData> lifecycleDataList,
            List<GoalData> goalDataList) {
        this.classLoader = classLoader;
        phaseDataList.forEach(v -> phaseDataMap.put(v.phaseName, v));
        lifecycleDataList.forEach(v -> lifecycleDataMap.put(v.lifecycleName, v));
        goalDataList.forEach(v -> goalDataMap.put(v.goalName, v));
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

                List<GoalHandle> goalHandleList = new ArrayList<>();
                for (GoalData goalData : goalDataMap.values()) {
                    if (goalData.phaseName.equals(phaseName)) {
                        Class goalClass = classLoader.loadClass(goalData.className);
                        String goalHandleName = phaseHandleName + "/" + goalData.goalName;
                        String goalRefName = phaseRefName + ":" + goalData.goalName;
                        Constructor goalConstructor = goalClass.getConstructor();
                        goalConstructor.setAccessible(true);
                        Object goalObject = goalConstructor.newInstance();
                        GoalHandle goalHandle = new GoalHandle(
                                goalHandleName,
                                getProcess(goalObject),
                                getComplete(goalObject),
                                getFormat(goalObject),
                                getInfo(goalObject));

                        goalHandleList.add(goalHandle);
                        goalHandleMap.put(goalRefName, goalHandle);
                        goalRefNameList.add(goalRefName);
                    }
                }

                Constructor phaseConstructor = phaseClass.getConstructor();
                phaseConstructor.setAccessible(true);
                Object phaseObject = phaseConstructor.newInstance();
                PhaseHandle phaseHandle = new PhaseHandle(
                        phaseHandleName,
                        getComplete(phaseObject),
                        getFormat(phaseObject),
                        getInfo(phaseObject),
                        goalHandleList
                );

                phaseHandleList.add(phaseHandle);
                phaseHandleMap.put(phaseHandleName, phaseHandle);
                phaseRefNameList.add(phaseRefName);

                phaseLifecycleMap.put(phaseRefName, lifecycleData.lifecycleName);
            }

            lifecyclePhaseHandleMap.put(lifecycleData.lifecycleName, phaseHandleList);
            lifecycleRefNameList.add(lifecycleData.lifecycleName);
        }

        for (GoalData goalData : goalDataMap.values()) {
            if (goalData.phaseName.equals(".null")) {
                Class goalClass = classLoader.loadClass(goalData.className);
                String goalHandleName = "/" + goalData.goalName;
                String goalRefName = ":" + goalData.goalName;
                Constructor goalConstructor = goalClass.getConstructor();
                goalConstructor.setAccessible(true);
                Object goalObject = goalConstructor.newInstance();
                GoalHandle goalHandle = new GoalHandle(
                        goalHandleName,
                        getProcess(goalObject),
                        getComplete(goalObject),
                        getFormat(goalObject),
                        getInfo(goalObject));

                goalHandleMap.put(goalRefName, goalHandle);
                goalRefNameList.add(goalRefName);
            }
        }
    }

    private ProcessInterface getProcess(Object object) {
        if (ProcessInterface.class.isAssignableFrom(object.getClass())) {
            return (ProcessInterface) object;
        }
        return null;
    }

    private CompleteInterface getComplete(Object object) {
        if (CompleteInterface.class.isAssignableFrom(object.getClass())) {
            return (CompleteInterface) object;
        }
        return null;
    }

    private FormatInterface getFormat(Object object) {
        if (FormatInterface.class.isAssignableFrom(object.getClass())) {
            return (FormatInterface) object;
        }
        return null;
    }

    private InfoInterface getInfo(Object object) {
        if (InfoInterface.class.isAssignableFrom(object.getClass())) {
            return (InfoInterface) object;
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
     * 获取阶段处理
     *
     * @param name 阶段名
     * @return 阶段处理列表 所在生命周期从第一个阶段到此阶段
     */
    public List<PhaseHandle> getPhaseHandle(String name) {
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
     * 获取目标处理
     *
     * @param name 目标名
     * @return
     */
    public GoalHandle getGoalHandle(String name) {
        return goalHandleMap.get(name);
    }

    public void close() {
        classLoader = null;
        phaseHandleMap = null;
        lifecyclePhaseHandleMap = null;
        goalHandleMap = null;
    }
}
