package view.collision;

import model.agent.Agent;
import model.level.Level;
import model.yogi.YogiBear;

import java.awt.Rectangle;

public class AgentCollisionHandler {
    private YogiBear yogi;
    private Level level;

    public AgentCollisionHandler(YogiBear yogi, Level level) {
        this.yogi = yogi;
        this.level = level;
    }

    // checks if yogi touched a guard or was spotted by one
    public boolean checkAgentCollisions() {
        Rectangle yogiBounds = yogi.getBounds();
        boolean caught = false;

        for (Agent agent : level.getAgents()) {
            if (!caught && yogiBounds.intersects(agent.getBounds())) {
                caught = true;
            }
            if (!caught && agent.canSeeYogi(yogi)) {
                caught = true;
            }
        }

        return caught;
    }
}
