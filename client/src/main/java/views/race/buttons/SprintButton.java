package views.race.buttons;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import race.Player;

public class SprintButton extends TextButton {

    private final Player player;
    private float cooldownTime = 0f;

    public SprintButton(Player player, String text, com.badlogic.gdx.scenes.scene2d.ui.Skin skin) {
        super(text, skin);
        this.player = player;

        addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (player.canSprint() && !isDisabled()) {
                    player.startSprint();
                    setDisabled(true);
                }
            }
        });
    }

    @Override
    public void act(float delta) {
        super.act(delta);

        boolean can = player.canSprint();
        boolean sprinting = player.isSprinting();
        boolean inCd = player.isInSprintCooldown();

        setDisabled(!can);

        float alpha = 1f;
        if (sprinting) {
            float t = Math.max(0f, player.getSprintTimeLeft()) / Math.max(0.0001f, player.getSprintDuration());
            alpha = 0.3f + 0.7f * t;
        } else if (inCd) {
            alpha = 0.35f;
        }
        getColor().a = alpha;
    }
}
