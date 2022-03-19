package si.examples.backtracking.model;

import java.util.Collection;

public interface Configuration {
    public Collection<Configuration> getSuccessors();
    public boolean isValid();
    public boolean isGoal();
}
