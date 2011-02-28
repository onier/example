/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package testframe;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.TexturePaint;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.dnd.DnDConstants;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.awt.geom.Line2D;
import java.awt.image.BufferedImage;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Vector;
import javax.swing.JComponent;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.SwingUtilities;
import javax.swing.tree.DefaultMutableTreeNode;
import layout.ShapeComponentListPanelRenderer;
import node.BlockBeanNodeElement;
import node.CaseBeanNodeElement;
import node.DoWhileBeanNodeElement;
import node.EndBeanNodeElement;
import node.ForBeanNodeElement;
import node.StartBeanNodeElement;
import node.SwitchBeanNodeElement;
import node.WhileBeanNodeElement;
import org.netbeans.api.visual.action.ActionFactory;
import org.netbeans.api.visual.action.ConnectProvider;
import org.netbeans.api.visual.action.ConnectorState;
import org.netbeans.api.visual.action.PopupMenuProvider;
import org.netbeans.api.visual.action.ReconnectProvider;
import org.netbeans.api.visual.action.SelectProvider;
import org.netbeans.api.visual.action.WidgetAction;
import org.netbeans.api.visual.anchor.AnchorFactory;
import org.netbeans.api.visual.anchor.AnchorShape;
import org.netbeans.api.visual.anchor.PointShape;
import org.netbeans.api.visual.graph.GraphScene;
import org.netbeans.api.visual.graph.layout.GraphLayout;
import org.netbeans.api.visual.graph.layout.GraphLayoutFactory;
import org.netbeans.api.visual.graph.layout.GraphLayoutSupport;
import org.netbeans.api.visual.layout.LayoutFactory;
import org.netbeans.api.visual.layout.SceneLayout;
import org.netbeans.api.visual.router.Router;
import org.netbeans.api.visual.router.RouterFactory;
import org.netbeans.api.visual.widget.ConnectionWidget;
import org.netbeans.api.visual.widget.LayerWidget;
import org.netbeans.api.visual.widget.Scene;
import org.netbeans.api.visual.widget.Widget;
import org.netbeans.api.visual.widget.general.IconNodeWidget;
import org.openide.util.Exceptions;
import org.openide.util.Utilities;
import shape.AbstractBeanNodeElement;
import shape.BeanNodeElement;
import shape.NodeElement;
import widget.BlockWidget;
import widget.CaseWidget;
import widget.DoWhileWidget;
import widget.EndWidget;
import widget.ForWidget;
import widget.NodeConnectionWidget;
import widget.StartWidget;
import widget.SwitchWidget;
import widget.WhileWidget;
import widget.WidgetInfo;
import widget.WidgetUtils;

/**
 *
 * @author admin
 */
public class BeanNodeGraphView extends GraphScene<BeanNodeElement, NodeConnection> {

    private static final Image IMAGE = Utilities.loadImage("test/resources/node.png");
    private LayerWidget mainLayer;
    private LayerWidget connectionLayer;
    private LayerWidget interractionLayer = new LayerWidget(this);
    private LayerWidget backgroundLayer = new LayerWidget(this);
    private WidgetAction moveAction = ActionFactory.createMoveAction();
    private Router router = RouterFactory.createFreeRouter();
    private WidgetAction connectAction = ActionFactory.createExtendedConnectAction(interractionLayer, new SceneConnectProvider(this));
    private WidgetAction reconnectAction = ActionFactory.createReconnectAction(new SceneReconnectProvider(this));
    private WidgetAction moveControlPointAction = ActionFactory.createFreeMoveControlPointAction();
//    private WidgetAction selectAction = ActionFactory.createSelectAction(new ObjectSelectProvider());
    private JComponent component;
    protected WidgetAction repaintAction = new WidgetAdapter() {

        public State mouseClicked(Widget widget, WidgetMouseEvent wme) {
            component.repaint();
            return State.CONSUMED;
        }

        public State mousePressed(Widget widget, WidgetMouseEvent wme) {
            component.repaint();
            return State.CONSUMED;
        }

        public State mouseReleased(Widget widget, WidgetMouseEvent wme) {
            component.repaint();
            return State.CONSUMED;
        }

        public State mouseEntered(Widget widget, WidgetMouseEvent wme) {
            component.repaint();
            return State.CONSUMED;
        }

        public State mouseExited(Widget widget, WidgetMouseEvent wme) {
            component.repaint();
            return State.CONSUMED;
        }

        @Override
        public State mouseDragged(Widget widget, WidgetMouseEvent wme) {
            component.repaint();
            return State.CONSUMED;
        }

        public State mouseMoved(Widget widget, WidgetMouseEvent wme) {
            component.repaint();
            return State.CONSUMED;
        }

        public State mouseWheelMoved(Widget widget, WidgetMouseWheelEvent wmwe) {
            component.repaint();
            return State.CONSUMED;
        }

        public State keyTyped(Widget widget, WidgetKeyEvent wke) {
            component.repaint();
            return State.CONSUMED;
        }

        public State keyPressed(Widget widget, WidgetKeyEvent wke) {
            component.repaint();
            return State.CONSUMED;
        }

        public State keyReleased(Widget widget, WidgetKeyEvent wke) {
            component.repaint();
            return State.CONSUMED;
        }

        public State focusGained(Widget widget, WidgetFocusEvent wfe) {
            component.repaint();
            return State.CONSUMED;
        }

        public State focusLost(Widget widget, WidgetFocusEvent wfe) {
            component.repaint();
            return State.CONSUMED;
        }

        public State dragEnter(Widget widget, WidgetDropTargetDragEvent wdtde) {
            component.repaint();
            return State.CONSUMED;
        }

        public State dragOver(Widget widget, WidgetDropTargetDragEvent wdtde) {
            component.repaint();
            return State.CONSUMED;
        }

        public State dropActionChanged(Widget widget, WidgetDropTargetDragEvent wdtde) {
            component.repaint();
            return State.CONSUMED;
        }

        public State dragExit(Widget widget, WidgetDropTargetEvent wdte) {
            component.repaint();
            return State.CONSUMED;
        }

        public State drop(Widget widget, WidgetDropTargetDropEvent wdtde) {
            component.repaint();
            return State.CONSUMED;
        }
    };
    private WidgetAction selectAction = new WidgetAdapter() {

        @Override
        public State mouseDragged(Widget widget, WidgetMouseEvent wme) {
            component.repaint();
            return super.mouseDragged(widget, wme);
        }

        @Override
        public State mouseMoved(Widget widget, WidgetMouseEvent wme) {
            component.repaint();
            return super.mouseMoved(widget, wme);
        }

        @Override
        public State mouseClicked(Widget widget, WidgetMouseEvent wme) {
            if (widget instanceof BlockWidget) {
                BlockWidget blockWidget = (BlockWidget) widget;
                Properties.getProperties().setProperties(blockWidget.getBeanNode().getPropertiesModel());
            }
            if (widget instanceof CaseWidget) {
                CaseWidget caseWidget = (CaseWidget) widget;
                Properties.getProperties().setProperties(caseWidget.getCaseNode().getPropertiesModel());
            }
            if (widget instanceof DoWhileWidget) {
                DoWhileWidget doWhileWidget = (DoWhileWidget) widget;
                Properties.getProperties().setProperties(doWhileWidget.getDoWhileNode().getPropertiesModel());
            }
            if (widget instanceof EndWidget) {
                EndWidget endWidget = (EndWidget) widget;
                Properties.getProperties().setProperties(endWidget.getBeanNode().getPropertiesModel());
            }
            if (widget instanceof StartWidget) {
                StartWidget startWidget = (StartWidget) widget;
                Properties.getProperties().setProperties(startWidget.getBeanNode().getPropertiesModel());
            }
            if (widget instanceof SwitchWidget) {
                SwitchWidget switchWidget = (SwitchWidget) widget;
                Properties.getProperties().setProperties(switchWidget.getSwitchNode().getPropertiesModel());
            }
            if (widget instanceof WhileWidget) {
                WhileWidget whileWidget = (WhileWidget) widget;
                Properties.getProperties().setProperties(whileWidget.getWhileNode().getPropertiesModel());
            }
            if (widget instanceof ForWidget) {
                ForWidget forWidget = (ForWidget) widget;
                Properties.getProperties().setProperties(forWidget.getForNode().getPropertiesModel());
            }
            return super.mouseClicked(widget, wme);
        }
    };
    private NodeMenu nodeMenu = new NodeMenu(this);
    private EdgeMenu edgeMenu = new EdgeMenu(this);
    private Point point;
    protected SceneLayout sceneGraphLayout;
    private StartBeanNodeElement startNode;
    private ArrayList<WidgetInfo> widgetArray = new ArrayList<WidgetInfo>();
    private String code = "";
    private PropertyChangeSupport support = new PropertyChangeSupport(this);

    public BeanNodeGraphView() {
        mainLayer = new LayerWidget(this);
        addChild(backgroundLayer);
        addChild(mainLayer);
        connectionLayer = new LayerWidget(this);
        addChild(connectionLayer);
        addChild(interractionLayer);
//        GridGraphLayout<NodeElement, NodeConnection> graphLayout = new GridGraphLayout<NodeElement, NodeConnection>();
//        sceneGraphLayout = LayoutFactory.createSceneGraphLayout(this, graphLayout);
        getActions().addAction(ActionFactory.createRectangularSelectAction(this, backgroundLayer));
//        getActions().addAction(ActionFactory.createPopupMenuAction(new SceneMainMenu(this)));
        getActions().addAction(ActionFactory.createMouseCenteredZoomAction(1.1));
        getActions().addAction(ActionFactory.createMoveAction(ActionFactory.createSnapToGridMoveStrategy(WidgetUtils.WIDTH, WidgetUtils.HEIGHT), null));
        getActions().addAction(ActionFactory.createPanAction());
        getActions().addAction(new WidgetAdapter() {

            @Override
            public State mouseClicked(Widget widget, WidgetMouseEvent wme) {
                return super.mouseClicked(widget, wme);
            }

            @Override
            public State mouseReleased(Widget widget, WidgetMouseEvent wme) {
                createView().repaint();
                return super.mouseReleased(widget, wme);
            }

            @Override
            public State mouseMoved(Widget widget, WidgetMouseEvent wme) {
                createView().repaint();
                return super.mouseMoved(widget, wme);
            }

            @Override
            public State mouseDragged(Widget widget, WidgetMouseEvent wme) {
                createView().repaint();
                return super.mouseDragged(widget, wme);
            }

            @Override
            public State drop(Widget widget, WidgetDropTargetDropEvent wdtde) {
                point = wdtde.getPoint();
                Transferable tr = wdtde.getTransferable();
                if (tr.isDataFlavorSupported(ShapeComponentListPanelRenderer.DEFAULT_ELEMENT_FLAVOR)) {
                    wdtde.acceptDrop(DnDConstants.ACTION_COPY_OR_MOVE);
                    final NodeElement userObject;
                    try {
                        userObject = (NodeElement) tr.getTransferData(ShapeComponentListPanelRenderer.DEFAULT_ELEMENT_FLAVOR);
                        addNode((AbstractBeanNodeElement) userObject);
                    } catch (UnsupportedFlavorException ex) {
                        Exceptions.printStackTrace(ex);
                    } catch (IOException ex) {
                        Exceptions.printStackTrace(ex);
                    }
                }
                return State.CONSUMED;
            }
        });
        initGrids();
    }

    public void addPropertyChangeListener(PropertyChangeListener l) {
        support.addPropertyChangeListener(l);
    }

    public void addPropertyChangeListener(String name, PropertyChangeListener l) {
        support.addPropertyChangeListener(name, l);
    }

    public PropertyChangeListener[] getPropertyChangeListeners() {
        return support.getPropertyChangeListeners();
    }

    public PropertyChangeListener[] getPropertyChangeListeners(String name) {
        return support.getPropertyChangeListeners(name);
    }

    public void removePropertyChangeListener(PropertyChangeListener l) {
        support.removePropertyChangeListener(l);
    }

    public void removePropertyChangeListener(String name, PropertyChangeListener l) {
        support.removePropertyChangeListener(name, l);
    }

    public String toCode() {
        code = "";
        if (startNode != null) {
            code = startNode.toString();
            parseCode(startNode);
        }
        return code;
    }

    public void parseCode(NodeElement e) {
        Collection<NodeConnection> collection = this.getEdges();
        for (NodeConnection connection : collection) {
            if (connection.getTarget().equals(e)) {
                code = code + connection.getSource().toString();
                parseCode(connection.getSource());
            }
        }
    }

    public static void createChildren(DefaultMutableTreeNode parent,
            Object children) {
        if (children instanceof Vector) {
            Vector childVector = (Vector) children;
            for (int counter = 0, maxCounter = childVector.size();
                    counter < maxCounter; counter++) {
                parent.add(new DefaultMutableTreeNode(childVector.elementAt(counter)));
            }
        } else if (children instanceof Map) {
            Map childHT = (Map) children;
            Iterator keys = childHT.keySet().iterator();
            Object aKey;
            while (keys.hasNext()) {
                aKey = keys.next();
                parent.add(new DefaultMutableTreeNode(aKey + " : " + childHT.get(aKey).toString()));
            }
        } else if (children instanceof Object[]) {
            Object[] childArray = (Object[]) children;
            for (int counter = 0, maxCounter = childArray.length;
                    counter < maxCounter; counter++) {
                parent.add(new DefaultMutableTreeNode(childArray[counter]));
            }
        } else if (children instanceof List) {
            List childArray = (List) children;

            for (int counter = 0; counter < childArray.size(); counter++) {
                parent.add(new DefaultMutableTreeNode(childArray.get(counter)));
            }
        }
    }

    public DefaultMutableTreeNode createTreeRoot() {
        DefaultMutableTreeNode root = new DefaultMutableTreeNode();
        if (startNode != null && startNode.getParameters().size() > 0) {
            DefaultMutableTreeNode parameter = new DefaultMutableTreeNode(startNode);
            createChildren(parameter, startNode.getParameters());
            root.add(parameter);
        }
        DefaultMutableTreeNode node = new DefaultMutableTreeNode("Node");
        createChildren(node, widgetArray);
        root.add(node);
        return root;
    }

    public void invokeLayout() {
        if (startNode != null) {
            GraphLayout<BeanNodeElement, NodeConnection> graphLayout = GraphLayoutFactory.createTreeGraphLayout(100, 100, 50, 50, true);
            GraphLayoutSupport.setTreeGraphLayoutRootNode(graphLayout, startNode);
            sceneGraphLayout = LayoutFactory.createSceneGraphLayout(BeanNodeGraphView.this, graphLayout);
            sceneGraphLayout.invokeLayout();
        }
    }

    @Override
    public JComponent createView() {
        if (component == null) {
            component = super.createView();
            component.addMouseMotionListener(new MouseMotionListener() {

                public void mouseDragged(MouseEvent e) {
                    component.repaint();
                }

                public void mouseMoved(MouseEvent e) {
                    component.repaint();
                }
            });
            component.addMouseListener(new MouseAdapter() {

                @Override
                public void mousePressed(MouseEvent e) {
                    component.requestFocusInWindow();
                }
            });
        }
        return component;
    }

    private Widget getWidget(BeanNodeElement node) {
        if (node instanceof BlockBeanNodeElement) {
            BlockWidget widget = new BlockWidget(this, (BlockBeanNodeElement) node);
            widget.setLabel(node.getDisctription());
            widget.setOpaque(true);
            widget.getActions().addAction(getConnectAction());
            widget.getActions().addAction(getMoveAction());
            widget.getActions().addAction(selectAction);
            if (point != null) {
                widget.setPreferredLocation(WidgetUtils.getPosition(point));
            }
            mainLayer.addChild(widget);
            widgetArray.add(widget);
            return widget;
        }
        if (node instanceof CaseBeanNodeElement) {
            CaseWidget widget = new CaseWidget(this, (CaseBeanNodeElement) node);
//            widget.setLabel(node.getDisctription());
            widget.setOpaque(true);
            widget.getActions().addAction(getConnectAction());
            widget.getActions().addAction(getMoveAction());
            widget.getActions().addAction(selectAction);
            if (point != null) {
                widget.setPreferredLocation(WidgetUtils.getPosition(point));
            }
            mainLayer.addChild(widget);
            widgetArray.add(widget);
            return widget;
        }
        if (node instanceof DoWhileBeanNodeElement) {
            DoWhileWidget widget = new DoWhileWidget(this, (DoWhileBeanNodeElement) node);
            widget.setOpaque(true);
            widget.getActions().addAction(selectAction);
            widget.getActions().addAction(getConnectAction());
            widget.getActions().addAction(getMoveAction());
            if (point != null) {
                widget.setPreferredLocation(WidgetUtils.getPosition(point));
            }
            mainLayer.addChild(widget);
            widgetArray.add(widget);
            return widget;
        }
        if (node instanceof EndBeanNodeElement) {
            EndWidget widget = new EndWidget(this, (EndBeanNodeElement) node);
            widget.setOpaque(true);
            widget.getActions().addAction(getConnectAction());
            widget.getActions().addAction(getMoveAction());
            widget.getActions().addAction(selectAction);
            if (point != null) {
                widget.setPreferredLocation(WidgetUtils.getPosition(point));
            }
            mainLayer.addChild(widget);
            widgetArray.add(widget);
            return widget;
        }
        if (node instanceof StartBeanNodeElement) {
            startNode = (StartBeanNodeElement) node;
            StartWidget widget = new StartWidget(this, (StartBeanNodeElement) node);
            widget.setOpaque(true);
            widget.getActions().addAction(getConnectAction());
            widget.getActions().addAction(getMoveAction());
            widget.getActions().addAction(selectAction);
            if (point != null) {
                widget.setPreferredLocation(WidgetUtils.getPosition(point));
            }
            mainLayer.addChild(widget);
            widgetArray.add(widget);
            return widget;
        }
        if (node instanceof SwitchBeanNodeElement) {
            SwitchWidget widget = new SwitchWidget(this, (SwitchBeanNodeElement) node, 0);
            widget.setOpaque(true);
            widget.getActions().addAction(selectAction);
            widget.getActions().addAction(getConnectAction());
            widget.getActions().addAction(getMoveAction());
            widget.getActions().addAction(getRepaintAction());
            if (point != null) {
                widget.setPreferredLocation(WidgetUtils.getPosition(point));
            }
            mainLayer.addChild(widget);
            widgetArray.add(widget);
            return widget;
        }
        if (node instanceof WhileBeanNodeElement) {
            WhileWidget widget = new WhileWidget(this, (WhileBeanNodeElement) node);
            widget.setOpaque(true);
            widget.getActions().addAction(selectAction);
            widget.getActions().addAction(getConnectAction());
            widget.getActions().addAction(getMoveAction());
            if (point != null) {
                widget.setPreferredLocation(WidgetUtils.getPosition(point));
            }
            mainLayer.addChild(widget);
            widgetArray.add(widget);
            return widget;
        }
        if (node instanceof ForBeanNodeElement) {
            ForWidget widget = new ForWidget(this, (ForBeanNodeElement) node);
            widget.setOpaque(true);
            widget.getActions().addAction(selectAction);
            widget.getActions().addAction(getConnectAction());
            widget.getActions().addAction(getMoveAction());
            if (point != null) {
                widget.setPreferredLocation(WidgetUtils.getPosition(point));
            }
            mainLayer.addChild(widget);
            widgetArray.add(widget);
            return widget;
        }
        return null;
    }

    @Override
    protected Widget attachNodeWidget(final BeanNodeElement node) {
        Widget w = getWidget(node);
        node.addPropertyChangeListener(new PropertyChangeListener() {

            public void propertyChange(PropertyChangeEvent evt) {
                support.firePropertyChange(evt);
                if (!evt.getPropertyName().equals("setProperties")) {
                    SwingUtilities.invokeLater(new Runnable() {

                        public void run() {
                            Navigator.getNavigator().setNavigatorProperty(createTreeRoot());
                        }
                    });
                }
            }
        });
        support.firePropertyChange("addWidget", null, w);
        Navigator.getNavigator().setNavigatorProperty(createTreeRoot());
        return w;
    }

    @Override
    protected Widget attachEdgeWidget(NodeConnection e) {
        NodeConnectionWidget connection = new NodeConnectionWidget(e, this);
        connection.setRouter(router);
        connection.setToolTipText("Double-click for Add/Remove Control Point");
        connection.setTargetAnchorShape(AnchorShape.TRIANGLE_FILLED);
        connection.setControlPointShape(PointShape.SQUARE_FILLED_BIG);
        connection.setEndPointShape(PointShape.SQUARE_FILLED_BIG);
        connectionLayer.addChild(connection);
        connection.getActions().addAction(reconnectAction);
        connection.getActions().addAction(createSelectAction());
        connection.getActions().addAction(ActionFactory.createAddRemoveControlPointAction());
        connection.getActions().addAction(moveControlPointAction);
        connection.getActions().addAction(ActionFactory.createPopupMenuAction(edgeMenu));
        return connection;
    }

    @Override
    protected void attachEdgeSourceAnchor(NodeConnection e, BeanNodeElement n, BeanNodeElement n1) {
        ConnectionWidget widget = (ConnectionWidget) findWidget(e);
        Widget sourceNodeWidget = findWidget(n1);
        widget.setSourceAnchor(sourceNodeWidget != null ? AnchorFactory.createFreeRectangularAnchor(sourceNodeWidget, true) : null);
    }

    @Override
    protected void attachEdgeTargetAnchor(NodeConnection e, BeanNodeElement n, BeanNodeElement n1) {
        ConnectionWidget widget = (ConnectionWidget) findWidget(e);
        Widget targetNodeWidget = findWidget(n1);
        widget.setTargetAnchor(targetNodeWidget != null ? AnchorFactory.createFreeRectangularAnchor(targetNodeWidget, true) : null);
    }

    public void initGrids() {
        BufferedImage image = new BufferedImage(WidgetUtils.WIDTH, WidgetUtils.HEIGHT, BufferedImage.TYPE_INT_RGB);
        Graphics2D graphics = image.createGraphics();
        Rectangle rect = new Rectangle(WidgetUtils.SPACE, WidgetUtils.SPACE, WidgetUtils.WIDTH - 2 * WidgetUtils.SPACE, WidgetUtils.HEIGHT - 2 * WidgetUtils.SPACE);
        graphics.setStroke(new BasicStroke(WidgetUtils.GRID_SPACE));
        graphics.setColor(Color.WHITE);
        graphics.fill(new Rectangle(0, 0, WidgetUtils.WIDTH, WidgetUtils.HEIGHT));
        graphics.setColor(WidgetUtils.color);
        graphics.drawRoundRect(rect.x, rect.y, rect.width, rect.height, 5, 5);
        graphics.dispose();
        TexturePaint PAINT_BACKGROUND = new TexturePaint(image, new Rectangle(0, 0, WidgetUtils.WIDTH, WidgetUtils.HEIGHT));
        setBackground(PAINT_BACKGROUND);
        repaint();
        revalidate(false);
        validate();
    }

    /**
     * @return the moveAction
     */
    public WidgetAction getMoveAction() {
        return moveAction;
    }

    /**
     * @return the connectAction
     */
    public WidgetAction getConnectAction() {
        return connectAction;
    }

    /**
     * @return the selectAction
     */
    public WidgetAction getSelectAction() {
        return selectAction;
    }

    /**
     * @return the repaintAction
     */
    public WidgetAction getRepaintAction() {
        return repaintAction;
    }

    class ObjectSelectProvider implements SelectProvider {

        public boolean isAimingAllowed(Widget widget, Point localLocation, boolean invertSelection) {
            return false;
        }

        public boolean isSelectionAllowed(Widget widget, Point localLocation, boolean invertSelection) {
            return true;
        }

        public void select(Widget widget, Point localLocation, boolean invertSelection) {
            Object object = findObject(widget);

            if (object != null) {
                if (getSelectedObjects().contains(object)) {
                    return;
                }
                userSelectionSuggested(Collections.singleton(object), invertSelection);
            } else {
                userSelectionSuggested(Collections.emptySet(), invertSelection);
            }
        }
    }
}

class EdgeMenu implements PopupMenuProvider, ActionListener {

    private static final String ADD_REMOVE_CP_ACTION = "addRemoveCPAction"; // NOI18N
    private static final String DELETE_TRANSITION = "deleteTransition"; // NOI18N
    private BeanNodeGraphView scene;
    private JPopupMenu menu;
    private ConnectionWidget edge;
    private Point point;

    public EdgeMenu(BeanNodeGraphView scene) {
        this.scene = scene;
        menu = new JPopupMenu("Transition Menu");
        JMenuItem item;

        item = new JMenuItem("Add/Delete Control Point");
        item.setActionCommand(ADD_REMOVE_CP_ACTION);
        item.addActionListener(this);
        menu.add(item);

        menu.addSeparator();

        item = new JMenuItem("Delete Transition");
        item.setActionCommand(DELETE_TRANSITION);
        item.addActionListener(this);
        menu.add(item);

    }

    public JPopupMenu getPopupMenu(Widget widget, Point point) {
        if (widget instanceof ConnectionWidget) {
            this.edge = (ConnectionWidget) widget;
            this.point = point;
            return menu;
        }
        return null;
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals(ADD_REMOVE_CP_ACTION)) {
            addRemoveControlPoint(point);
        } else if (e.getActionCommand().equals(DELETE_TRANSITION)) {
            scene.removeEdge((NodeConnection) scene.findObject(edge));
        }
    }

    private void addRemoveControlPoint(Point localLocation) {
        ArrayList<Point> list = new ArrayList<Point>(edge.getControlPoints());
        double createSensitivity = 1.00, deleteSensitivity = 5.00;
        if (!removeControlPoint(localLocation, list, deleteSensitivity)) {
            Point exPoint = null;
            int index = 0;
            for (Point elem : list) {
                if (exPoint != null) {
                    Line2D l2d = new Line2D.Double(exPoint, elem);
                    if (l2d.ptLineDist(localLocation) < createSensitivity) {
                        list.add(index, localLocation);
                        break;
                    }
                }
                exPoint = elem;
                index++;
            }
        }
        edge.setControlPoints(list, false);
    }

    private boolean removeControlPoint(Point point, ArrayList<Point> list, double deleteSensitivity) {
        for (Point elem : list) {
            if (elem.distance(point) < deleteSensitivity) {
                list.remove(elem);
                return true;
            }
        }
        return false;
    }
}

class NodeMenu implements PopupMenuProvider, ActionListener {

    private static final String DELETE_NODE_ACTION = "deleteNodeAction"; // NOI18N
    private JPopupMenu menu;
    private IconNodeWidget node;
    private Point point;
    private BeanNodeGraphView scene;

    public NodeMenu(BeanNodeGraphView scene) {
        this.scene = scene;
        menu = new JPopupMenu("Node Menu");
        JMenuItem item;

        item = new JMenuItem("Delete Node");
        item.setActionCommand(DELETE_NODE_ACTION);
        item.addActionListener(this);
        menu.add(item);
    }

    public JPopupMenu getPopupMenu(Widget widget, Point point) {
        this.point = point;
        this.node = (IconNodeWidget) widget;
        return menu;
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals(DELETE_NODE_ACTION)) {
            scene.removeNodeWithEdges((BeanNodeElement) scene.findObject(node));
            scene.validate();
        }
    }
}

class SceneConnectProvider implements ConnectProvider {

    private BeanNodeElement source = null;
    private BeanNodeElement target = null;
    private BeanNodeGraphView scene;

    public SceneConnectProvider(BeanNodeGraphView scene) {
        this.scene = scene;
    }

    public boolean isSourceWidget(Widget sourceWidget) {
        Object object = scene.findObject(sourceWidget);
        source = scene.isNode(object) ? (BeanNodeElement) object : null;
        return source != null;
    }

    public ConnectorState isTargetWidget(Widget sourceWidget, Widget targetWidget) {
        Object object = scene.findObject(targetWidget);
        target = scene.isNode(object) ? (BeanNodeElement) object : null;
        if (target != null) {
            return !source.equals(target) ? ConnectorState.ACCEPT : ConnectorState.REJECT_AND_STOP;
        }
        return object != null ? ConnectorState.REJECT_AND_STOP : ConnectorState.REJECT;
    }

    public boolean hasCustomTargetWidgetResolver(Scene scene) {
        return false;
    }

    public Widget resolveTargetWidget(Scene scene, Point sceneLocation) {
        return null;
    }

    public void createConnection(Widget sourceWidget, Widget targetWidget) {
        NodeConnection con = new NodeConnection(source, target, scene);
        scene.addEdge(con);
        scene.setEdgeSource(con, source);
        scene.setEdgeTarget(con, target);
        scene.validate();
    }
}

class SceneReconnectProvider implements ReconnectProvider {

    private NodeConnection edge;
    private BeanNodeElement originalNode;
    private BeanNodeElement replacementNode;
    private BeanNodeGraphView scene;

    public SceneReconnectProvider(BeanNodeGraphView scene) {
        this.scene = scene;
    }

    public void reconnectingStarted(ConnectionWidget connectionWidget, boolean reconnectingSource) {
    }

    public void reconnectingFinished(ConnectionWidget connectionWidget, boolean reconnectingSource) {
    }

    public boolean isSourceReconnectable(ConnectionWidget connectionWidget) {
        Object object = scene.findObject(connectionWidget);
        edge = scene.isEdge(object) ? (NodeConnection) object : null;
        originalNode = edge != null ? (BeanNodeElement) scene.getEdgeSource(edge) : null;
        return originalNode != null;
    }

    public boolean isTargetReconnectable(ConnectionWidget connectionWidget) {
        Object object = scene.findObject(connectionWidget);
        edge = scene.isEdge(object) ? (NodeConnection) object : null;
        originalNode = edge != null ? (BeanNodeElement) scene.getEdgeTarget(edge) : null;
        return originalNode != null;
    }

    public ConnectorState isReplacementWidget(ConnectionWidget connectionWidget, Widget replacementWidget, boolean reconnectingSource) {
        Object object = scene.findObject(replacementWidget);
        replacementNode = scene.isNode(object) ? (BeanNodeElement) object : null;
        if (replacementNode != null) {
            return ConnectorState.ACCEPT;
        }
        return object != null ? ConnectorState.REJECT_AND_STOP : ConnectorState.REJECT;
    }

    public boolean hasCustomReplacementWidgetResolver(Scene scene) {
        return false;
    }

    public Widget resolveReplacementWidget(Scene scene, Point sceneLocation) {
        return null;
    }

    public void reconnect(ConnectionWidget connectionWidget, Widget replacementWidget, boolean reconnectingSource) {
        if (replacementWidget == null) {
            scene.removeEdge(edge);
        } else if (reconnectingSource) {
            scene.setEdgeSource(edge, replacementNode);
        } else {
            scene.setEdgeTarget(edge, replacementNode);
        }
        scene.validate();
    }
}
