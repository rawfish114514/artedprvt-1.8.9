package rawfish.artedprvt.work;

public class ProjectSystem {
    private Project project;

    public ProjectSystem(Project project) {
        this.project = project;
    }

    public String getDir() {
        return project.getDir();
    }


}
