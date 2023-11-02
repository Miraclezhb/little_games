import java.awt.*;
import java.util.ArrayList;
import java.util.LinkedList;

/**
 * 贪吃蛇定位
 * 存储所有贪吃蛇身体的位置信息
 */
public class SnakePosition {
    /**
     * 存储蛇头节点
     */
    private final SnakeNode snakeHead;

    /**
     * 存储蛇尾节点
     */
    private SnakeNode snakeTail;

    private final ArrayList<SnakeNode> snakeNodes = new ArrayList<>();

    public SnakePosition(int x, int y, Direction direction) {
        PositionXY positionXY = new PositionXY(x, y);
        this.snakeHead = new SnakeNode(SnakeNodeType.HEAD, positionXY);
        this.snakeHead.direction = direction;
        this.snakeNodes.add(this.snakeHead);
        this.snakeTail = this.snakeHead;
    }

    public SnakePosition() {
        this(400, 300, Direction.RIGHT);
    }

    public void addNode() {
        int x = this.snakeTail.getX();
        int y = this.snakeTail.getY();
        PositionXY node = null;
        switch (this.snakeTail.direction) {
            case UP -> node = new PositionXY(x, y - SnakeConfig.SNAKE_NODE_HEIGHT);
            case DOWN -> node = new PositionXY(x, y + SnakeConfig.SNAKE_NODE_HEIGHT);
            case LEFT -> node = new PositionXY(x + SnakeConfig.SNAKE_NODE_WIDTH, y);
            case RIGHT -> node = new PositionXY(x - SnakeConfig.SNAKE_NODE_WIDTH, y);
        }
        SnakeNode snakeNode = new SnakeNode(node);
        snakeNode.direction = this.snakeTail.direction;
        snakeNode.redirectQueue.addAll(this.snakeTail.redirectQueue);
        this.snakeNodes.add(snakeNode);
        this.snakeTail = snakeNode;
    }

    public void paintSnake(Graphics g) {
        g.setColor(Color.CYAN);
        for (SnakeNode snakeNode : this.snakeNodes) {
            g.fillOval(snakeNode.getX(), snakeNode.getY(), SnakeConfig.SNAKE_NODE_WIDTH, SnakeConfig.SNAKE_NODE_HEIGHT);
        }
    }

    /**
     * 贪吃蛇重定向，记录拐弯的坐标点，将需要拐弯的坐标放入每个贪吃蛇节点中
     *
     * @param nextDirection 拐弯方向
     */
    public void snakeNodeRedirect(Direction nextDirection) {
        if (this.snakeNodes.get(0).canTurnAround(nextDirection)) {
            PositionXY redirectPosition = new PositionXY(this.snakeHead.getX(), this.snakeHead.getY());
            redirectPosition.direction = nextDirection;
            this.snakeNodes.forEach(snakeNode -> snakeNode.redirectQueue.add(redirectPosition));
        }
    }

    /**
     * 处理贪吃蛇所有节点继续前进或者转向
     *
     * @return 返回 true 表示撞到了自己
     */
    public boolean handleSnakeNodes(BeanNode beanNode) {
        boolean isBump = false;
        for (SnakeNode snakeNode : this.snakeNodes) {
            if (!isBump && snakeNode.type != SnakeNodeType.HEAD) {
                isBump = judgeBump(this.snakeHead, snakeNode);
            }
            PositionXY redirectPosition = snakeNode.redirectQueue.peekFirst();
            if (redirectPosition != null && snakeNode.isTurnAround(redirectPosition)) {
                singleNodeMove(snakeNode, redirectPosition.direction);
                snakeNode.direction = snakeNode.redirectQueue.removeFirst().direction;
            } else {
                singleNodeMove(snakeNode, snakeNode.direction);
            }
        }
        // 贪吃蛇吃豆子逻辑
        if (judgeBump(this.snakeHead, beanNode)) {
            addNode();
            beanNode.updateXY();
        }
        return isBump;
    }

    /**
     * 判断是否碰撞的方法
     *
     * @param node1 一般是蛇头节点
     * @param node2 可能为蛇身，也可能为豆子
     * @return 返回 true 表示碰撞
     */
    private boolean judgeBump(SnakeNode node1, SnakeNode node2) {
        if (node1 == null || node2 == null) {
            return false;
        }
        int n1x = node1.getX();
        int n1y = node1.getY();
        int n2x = node2.getX();
        int n2y = node2.getY();
        double dis = Math.sqrt(Math.pow(Math.abs(n1y - n2y), 2) + Math.pow(Math.abs(n1x - n2x), 2));
        return dis <= SnakeConfig.snakeSpeed;
    }

    /**
     * 辅助方法，控制单个贪吃蛇节点移动
     *
     * @param snakeNode 需要移动的贪吃蛇节点
     * @param direction 需要移动的方向
     */
    private void singleNodeMove(SnakeNode snakeNode, Direction direction) {
        switch (direction) {
            case UP -> snakeNode.moveUp();
            case DOWN -> snakeNode.moveDown();
            case LEFT -> snakeNode.moveLeft();
            case RIGHT -> snakeNode.moveRight();
        }
    }
}

/**
 * 贪吃蛇节点类型
 */
enum SnakeNodeType {
    HEAD, BODY, BEAN
}

class PositionXY {
    int x;
    int y;
    Direction direction;

    public PositionXY(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public boolean isEqual(PositionXY position) {
        if (position == null) {
            return false;
        }
        return this.x == position.x && this.y == position.y;
    }
}

class SnakeNode {
    // 位置信息
    protected final PositionXY position;

    // 该节点所属类型
    public final SnakeNodeType type;

    // 需要转弯的坐标信息
    public final LinkedList<PositionXY> redirectQueue = new LinkedList<>();

    // 该节点下一步前进方向
    Direction direction;

    public SnakeNode(PositionXY position) {
        this(SnakeNodeType.BODY, position);
    }

    public SnakeNode(SnakeNodeType type, PositionXY position) {
        this.type = type;
        this.position = position;
    }

    public int getX() {
        return position.x;
    }

    public int getY() {
        return position.y;
    }

    /**
     * 向上移动
     */
    public void moveRight() {
        this.position.x += SnakeConfig.snakeSpeed;
        handleCriticality(Direction.RIGHT);
    }

    /**
     * 向左移动
     */
    public void moveLeft() {
        this.position.x -= SnakeConfig.snakeSpeed;
        handleCriticality(Direction.LEFT);
    }

    /**
     * 向左移动
     */
    public void moveUp() {
        this.position.y -= SnakeConfig.snakeSpeed;
        handleCriticality(Direction.UP);
    }

    /**
     * 向右移动
     */
    public void moveDown() {
        this.position.y += SnakeConfig.snakeSpeed;
        handleCriticality(Direction.DOWN);
    }

    /**
     * 处理临界情况
     *
     * @param direction 前进方向
     */
    private void handleCriticality(Direction direction) {
        switch (direction) {
            case UP -> {
                if (this.position.y < 0)
                    this.position.y += SnakeConfig.screenHeight;
            }
            case DOWN -> {
                if (this.position.y > SnakeConfig.screenHeight)
                    this.position.y -= SnakeConfig.screenHeight;
            }
            case LEFT -> {
                if (this.position.x < 0)
                    this.position.x += SnakeConfig.screenWidth;
            }
            case RIGHT -> {
                if (this.position.x > SnakeConfig.screenWidth)
                    this.position.x -= SnakeConfig.screenWidth;
            }
        }
    }

    /**
     * 判断当前位置和当前行进方向能够转向
     *
     * @param nextDirection 待转弯方向
     * @return true表示可以转向，false表示不能转向
     */
    public boolean canTurnAround(Direction nextDirection) {
        if (Direction.RIGHT.equals(this.direction) || Direction.LEFT.equals(this.direction)) {
            return Direction.UP.equals(nextDirection) || Direction.DOWN.equals(nextDirection);
        } else {
            return Direction.LEFT.equals(nextDirection) || Direction.RIGHT.equals(nextDirection);
        }
    }

    /**
     * 判断是否该转向
     *
     * @return true:需要转向，false:不需要转向
     */
    public boolean isTurnAround(PositionXY position) {
        return this.position.isEqual(position);
    }
}
