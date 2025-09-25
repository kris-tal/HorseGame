package race;

import data.horse.HorseType;
import race.strategy.sprint.SprintStrategy;
import race.strategy.player.PlayerMovementStrategy;
import race.strategy.player.PlayerVisionStrategy;
import race.strategy.sprint.SprintManager;
import race.strategy.player.StatusEffectManager;

public class Player {
    protected final HorseType horseType;
    protected int lane;
    protected float col = 0f;
    protected int coins = 0;
    protected float baseSpeed;
    protected boolean alive = true;
    protected boolean raceCompleted = false;

    protected boolean sprinting = false;
    protected float sprintTimeLeft = 0f;
    protected float sprintDuration = 2f;
    protected float sprintCooldown = 10f;
    protected float cooldownTimer = 0f;

    protected float slowDownTimeLeft = 0f;
    protected float slowDownFactor = 0.5f;
    protected float visionLimitTime = 0f;
    protected float visionLimitColsLeft = 0f;

    protected final SprintStrategy sprintStrategy;

    protected final PlayerMovementStrategy movementStrategy;

    protected final PlayerVisionStrategy visionStrategy;

    protected final SprintManager sprintManager;

    protected final StatusEffectManager statusEffectManager;

    public Player(HorseType horseType, int startingLane, float speed, 
                        SprintStrategy sprintStrategy, PlayerMovementStrategy movementStrategy, 
                        PlayerVisionStrategy visionStrategy, SprintManager sprintManager, 
                        StatusEffectManager statusEffectManager) {
        this.horseType = horseType;
        this.lane = startingLane;
        this.baseSpeed = speed;
        this.sprintStrategy = sprintStrategy;
        this.movementStrategy = movementStrategy;
        this.visionStrategy = visionStrategy;
        this.sprintManager = sprintManager;
        this.statusEffectManager = statusEffectManager;
    }

    public void moveUp() {
        if (lane < 2) lane++;
    }

    public void moveDown() {
        if (lane > 0) lane--;
    }

    public void update(float delta) {
        if (!alive) return;

        try {
            sprintManager.updateSprint(this, delta);
            movementStrategy.updateMovement(this, delta);
            visionStrategy.updateVision(this, delta);
            statusEffectManager.updateStatusEffects(this, delta);

        } catch (Exception e) {
            // Handle errors silently but we can change it
        }
    }
    public void startSprint() {
        sprintManager.startSprint(this);
    }

    public boolean canSprint() {
        return sprintManager.canSprint(this);
    }

    public float getSprintCooldownProgress() {
        return sprintStrategy.getSprintCooldownProgress(cooldownTimer, sprintCooldown);
    }

    public boolean isInSprintCooldown() {
        return !sprinting && cooldownTimer > 0f;
    }

    public void resetSprint() {
        sprintManager.resetSprint(this);
    }

    public void makeSprintAvailable() {
        sprintManager.makeSprintAvailable(this);
    }

    public void slowDown() {
        statusEffectManager.applySlowDown(this, 1.5f);
    }

    public void slowDown(float seconds) {
        statusEffectManager.applySlowDown(this, seconds);
    }

    public void limitVisionByMeters(float meters) {
        statusEffectManager.applyVisionLimitByMeters(this, meters);
    }

    public boolean isVisionLimited() {
        return statusEffectManager.isVisionLimited(this);
    }

    // Getters
    public int getLane() { return lane; }
    public float getCol() { return col; }
    public boolean isAlive() { return alive; }
    public float getSpeed() { return baseSpeed; }
    public int getCoins() { return coins; }
    public HorseType getHorseType() { return horseType; }
    
    // Getters for strategies
    public boolean isSprinting() { return sprinting; }
    public float getSprintTimeLeft() { return sprintTimeLeft; }
    public float getSprintDuration() { return sprintDuration; }
    public float getSprintCooldown() { return sprintCooldown; }
    public float getCooldownTimer() { return cooldownTimer; }
    public float getSlowDownTimeLeft() { return slowDownTimeLeft; }
    public float getSlowDownFactor() { return slowDownFactor; }
    public float getVisionLimitTime() { return visionLimitTime; }
    public float getVisionLimitColsLeft() { return visionLimitColsLeft; }
    public SprintStrategy getSprintStrategy() { return sprintStrategy; }
    
    // Setters for strategies
    public void setCol(float col) { this.col = col; }
    public void setSprinting(boolean sprinting) { this.sprinting = sprinting; }
    public void setSprintTimeLeft(float sprintTimeLeft) { this.sprintTimeLeft = sprintTimeLeft; }
    public void setCooldownTimer(float cooldownTimer) { this.cooldownTimer = cooldownTimer; }
    public void setSlowDownTimeLeft(float slowDownTimeLeft) { this.slowDownTimeLeft = slowDownTimeLeft; }
    public void setVisionLimitTime(float visionLimitTime) { this.visionLimitTime = visionLimitTime; }
    public void setVisionLimitColsLeft(float visionLimitColsLeft) { this.visionLimitColsLeft = visionLimitColsLeft; }

    // Kill the player
    public void kill() {
        alive = false;
    }

    public void success() {
        raceCompleted = true;
    }

    public void addCoins(int amount) {
        coins += amount;
    }
    
    public boolean isRaceCompleted() {
        return raceCompleted;
    }
}
