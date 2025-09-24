package race.strategy.training;

import race.strategy.sprint.AbstractSprintStrategy;

public class TrainingSprintStrategy extends AbstractSprintStrategy {
    private final float sprintCooldownReduction; // Percentage reduction (0.01 = 1%)

    public TrainingSprintStrategy(float sprintCooldownReduction) {
        this.sprintCooldownReduction = Math.max(0f, Math.min(1f, sprintCooldownReduction));
    }

    @Override
    public float calculateSprintCooldown(float baseCooldown) {
        return baseCooldown * (1f - sprintCooldownReduction);
    }
}
