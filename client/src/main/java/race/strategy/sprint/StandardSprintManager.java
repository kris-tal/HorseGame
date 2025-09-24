package race.strategy.sprint;

import race.Player;

public class StandardSprintManager implements SprintManager {
    
    @Override
    public void startSprint(Player player) {
        if (player.getSprintStrategy().canSprint(player.isSprinting(), player.getCooldownTimer())) {
            player.setSprinting(true);
            player.setSprintTimeLeft(player.getSprintDuration());
        }
    }
    
    @Override
    public boolean canSprint(Player player) {
        return player.getSprintStrategy().canSprint(player.isSprinting(), player.getCooldownTimer());
    }
    
    @Override
    public void resetSprint(Player player) {
        player.setSprinting(false);
        player.setCooldownTimer(player.getSprintStrategy().calculateSprintCooldown(player.getSprintCooldown()));
    }
    
    @Override
    public void makeSprintAvailable(Player player) {
        player.setSprinting(false);
        player.setCooldownTimer(0f);
    }
    
    @Override
    public void updateSprint(Player player, float delta) {
        if (player.isSprinting()) {
            player.setSprintTimeLeft(player.getSprintTimeLeft() - delta);
            if (player.getSprintTimeLeft() <= 0f) {
                player.setSprinting(false);
                player.setCooldownTimer(player.getSprintStrategy().calculateSprintCooldown(player.getSprintCooldown()));
            }
        }
        
        if (player.getCooldownTimer() > 0f) {
            player.setCooldownTimer(player.getCooldownTimer() - delta);
        }
    }
}




