package Helper;

import java.awt.*;

public class Resolution {
    Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    public double width = screenSize.getWidth();
    public double height = screenSize.getHeight();
}
