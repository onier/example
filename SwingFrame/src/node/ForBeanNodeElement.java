/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package node;

import java.beans.DefaultPersistenceDelegate;
import java.beans.Encoder;
import java.beans.Expression;
import java.util.List;
import java.util.TreeMap;
import javax.swing.ImageIcon;
import shape.AbstractBeanNodeElement;
import shape.BeanNodeElement;
import testframe.IconUtils;
import widget.WidgetUtils;

/**
 *
 * @author admin
 */
public class ForBeanNodeElement extends AbstractBeanNodeElement {

    public ForBeanNodeElement(BeanNodeElement parent, List<BeanNodeElement> children, TreeMap<String, Class> beanInfo, TreeMap<String, Object> beanValue, ImageIcon icon, String disctription) {
        super(parent, children, beanInfo, beanValue, icon, disctription);
    }

    public ForBeanNodeElement(ForBeanNodeElement e) {
        super(e);
    }

    public ForBeanNodeElement() {
        this.beanInfo.put("init", String.class);
        this.beanValue.put("init", "");
        this.beanInfo.put("Expression", String.class);
        this.beanValue.put("Expression", "");
        this.beanInfo.put("updata", String.class);
        this.beanValue.put("updata", "");
        this.beanInfo.put("block", String.class);
        this.beanValue.put("block", "");
        this.disctription = "For";
    }

    @Override
    public ImageIcon getIcon() {
        return IconUtils.getLoopIcon();
    }

    @Override
    public String toString() {
        String str = "for(";
        str = str + getBeanValue().get("init") + ";";
        str = str + getBeanValue().get("Expression").toString() + ";";
        str = str + getBeanValue().get("updata");
        str = str + "){" + "\n";
        str = str + getBeanValue().get("block") + "\n";
        str = str + "}" + "\n";
        return str;
    }

    public String toTipString() {
        String str = "for(";
        str = str + getBeanValue().get("init") + ";";
        str = str + WidgetUtils.getCaseString(getBeanValue().get("Expression").toString()) + ";";
        str = str + getBeanValue().get("updata");
        str = str + "){" + "\n";
        str = str + getBeanValue().get("block") + "\n";
        str = str + "}" + "\n";
        return str;
    }

    public BeanNodeElement getEditNode() {
        return new ForBeanNodeElement(this);
    }

    public void loadEncoderDelegate(Encoder encoder) {
        encoder.setPersistenceDelegate(ForBeanNodeElement.class, new DefaultPersistenceDelegate(new String[]{"parent", "children", "beanInfo", "beanValue", "icon", "disctription"}) {

            @Override
            protected Expression instantiate(Object oldInstance, Encoder out) {
                ForBeanNodeElement test = (ForBeanNodeElement) oldInstance;
                return new Expression(test, test.getClass(), "new", new Object[]{test.getParent(), test.getChildren(), test.getBeanInfo(), test.getBeanValue(), test.getIcon(), test.getDisctription()});
            }
        });
    }

    private static void parseFor(String str, ForBeanNodeElement e) {
        int s = str.indexOf("(");
        int n = str.lastIndexOf(")");
        if (s >= 0 && n >= 0) {
            str = str.substring(s + 1, n);
            String[] values = str.split(";");
            if (values.length == 3) {
                e.beanValue.put("init", values[0]);
                e.beanValue.put("Expression", values[1]);
                e.beanValue.put("updata", values[2]);
            }
        }
    }

    public static ForBeanNodeElement parseElement(String str) {
        ForBeanNodeElement e = new ForBeanNodeElement();
        int n = str.indexOf("{");
        if (n >= 0) {
            parseFor(str.substring(0, n), e);
            int m = str.lastIndexOf("}");
            e.beanValue.put("block", str.substring(n + 1, m).trim());
        }
        return e;
    }

    public static void main(String[] args) {
        String str = " for (int i = 0; i < 3; i++) {" + "\n"
                + "if (i > 2) {" + "\n"
                + "System.out.println(i);" + "\n"
                + "} else {" + "\n"
                + "i++;" + "\n"
                + "}" + "\n"
                + "}";
        ForBeanNodeElement e = new ForBeanNodeElement();
        System.out.println(e.parseElement(str));
    }
}
