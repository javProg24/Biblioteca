package main.resources.Shared.Notification;

import com.formdev.flatlaf.util.Animator;
import main.resources.Utils.ComponentFactory;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.image.BufferedImage;

public class NotificationComponent extends JComponent {
    private JButton cmdClose;
    private JLabel lblIcon;
    private JLabel lblMessage;
    private JLabel lblMessageText;
    private JPanel panel;
    private JDialog dialog;
    private Animator animator;
    private final Frame frame;
    private boolean showing;
    private Thread thread;
    private int animate=10;
    private BufferedImage imageShadow;
    private int shadowsSize=6;
    private Type type;
    private Location location;
    public NotificationComponent(Frame frame, Type type,Location location,String message){
        this.frame=frame;
        this.type=type;
        this.location=location;
        initComponents();
        init(message);
        initAnimator();
    }

    private void init(String message) {
        setBackground(Color.WHITE);
        dialog=new JDialog(frame);
        dialog.setUndecorated(true);
        dialog.setFocusableWindowState(false);
        dialog.setBackground(new Color(0,0,0,0));
        dialog.add(this);
        dialog.setSize(getPreferredSize());
        if(type==Type.EXITO){
            lblIcon.setIcon(new ImageIcon(ComponentFactory.ruta("success")));
            lblMessage.setText(Type.EXITO.toString());
            lblMessageText.setText(message);
        }else if(type==Type.INFORMACION){
            lblIcon.setIcon(new ImageIcon(ComponentFactory.ruta("info")));
            lblMessage.setText(Type.INFORMACION.toString());
            lblMessageText.setText(message);
        } else if (type==Type.ADVERTENCIA){
            lblIcon.setIcon(new ImageIcon(ComponentFactory.ruta("warning")));
            lblMessage.setText(Type.ADVERTENCIA.toString());
            lblMessageText.setText(message);
        }
    }
    public void showNotification() {
        animator.start();
    }
    private void initAnimator() {
        Animator.TimingTarget target = new Animator.TimingTarget() {
            private int x;
            private int top;
            private boolean top_to_bot;
            @Override
            public void timingEvent(float v) {
                if(showing){
                    float alpha=1f-v;
                    int y=(int)((1f-v)*animate);
                    if(top_to_bot){
                        dialog.setLocation(x,top+y);
                    }else {
                        dialog.setLocation(x,top-y);
                    }
                    dialog.setOpacity(alpha);
                }
                else {
                    int y=(int)(v*animate);
                    if(top_to_bot){
                        dialog.setLocation(x,top+y);
                    }
                    else {
                        dialog.setLocation(x,top-y);
                    }
                    dialog.setOpacity(v);
                }
            }

            @Override
            public void begin() {
                if (!showing) {
                    dialog.setOpacity(0f);
                    int margin = 10;
                    int y = 0;
                    if (location == Location.TOP_CENTER) {
                        x = frame.getX() + ((frame.getWidth() - dialog.getWidth()) / 2);
                        y = frame.getY();
                        top_to_bot = true;
                    } else if (location == Location.TOP_RIGHT) {
                        x = frame.getX() + frame.getWidth() - dialog.getWidth() - margin;
                        y = frame.getY();
                        top_to_bot = true;
                    } else if (location == Location.TOP_LEFT) {
                        x = frame.getX() + margin;
                        y = frame.getY();
                        top_to_bot = true;
                    } else if (location == Location.BOTTOM_CENTER) {
                        x = frame.getX() + ((frame.getWidth() - dialog.getWidth()) / 2);
                        y = frame.getY() + frame.getHeight() - dialog.getHeight();
                        top_to_bot = false;
                    } else if (location == Location.BOTTOM_RIGHT) {
                        x = frame.getX() + frame.getWidth() - dialog.getWidth() - margin;
                        y = frame.getY() + frame.getHeight() - dialog.getHeight();
                        top_to_bot = false;
                    } else if (location == Location.BOTTOM_LEFT) {
                        x = frame.getX() + margin;
                        y = frame.getY() + frame.getHeight() - dialog.getHeight();
                        top_to_bot = false;
                    } else {
                        x = frame.getX() + ((frame.getWidth() - dialog.getWidth()) / 2);
                        y = frame.getY() + ((frame.getHeight() - dialog.getHeight()) / 2);
                        top_to_bot = true;
                    }
                    top = y;
                    dialog.setLocation(x, y);
                    dialog.setVisible(true);
                }
            }

            @Override
            public void end() {
                showing = !showing;
                if (showing) {
                    thread = new Thread(new Runnable() {
                        @Override
                        public void run() {
                            sleep();
                            closeNotification();
                        }
                    });
                    thread.start();
                } else {
                    dialog.dispose();
                }
            }
        };
        animator = new Animator(500, target);
        animator.setResolution(5);
    }
    private void closeNotification() {
        if (thread != null && thread.isAlive()) {
            thread.interrupt();
        }
        if (animator.isRunning()) {
            if (!showing) {
                animator.stop();
                showing = true;
                animator.start();
            }
        } else {
            showing = true;
            animator.start();
        }
    }

    private void sleep() {
        try {
            Thread.sleep(2000);
        } catch (InterruptedException ignored) {

        }
    }
    @Override
    public void paint(Graphics grphcs) {
        Graphics2D g2 = (Graphics2D) grphcs.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setColor(getBackground());
        g2.drawImage(imageShadow, 0, 0, null);
        int x = shadowsSize;
        int y = shadowsSize;
        int width = getWidth() - shadowsSize * 2;
        int height = getHeight() - shadowsSize * 2;
        g2.fillRect(x, y, width, height);
        if (type == Type.EXITO) {
            g2.setColor(new Color(18, 163, 24));
        } else if (type == Type.INFORMACION) {
            g2.setColor(new Color(28, 139, 206));
        } else {
            g2.setColor(new Color(241, 196, 15));
        }
        g2.fillRect(6, 5, 5, getHeight() - shadowsSize * 2 + 1);
        g2.dispose();
        super.paint(grphcs);
    }
    @Override
    public void setBounds(int i, int i1, int i2, int i3) {
        super.setBounds(i, i1, i2, i3);
        createImageShadow();
    }

    private void createImageShadow() {
        imageShadow = new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2 = imageShadow.createGraphics();
        g2.drawImage(createShadow(), 0, 0, null);
        g2.dispose();
    }
    private BufferedImage createShadow() {
        BufferedImage img = new BufferedImage(getWidth() - shadowsSize * 2, getHeight() - shadowsSize * 2, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2 = img.createGraphics();
        g2.fillRect(0, 0, img.getWidth(), img.getHeight());
        g2.dispose();
        return new ShadowRenderer(shadowsSize, 0.3f, new Color(100, 100, 100)).createShadow(img);
    }
    private void initComponents() {
        lblIcon = new JLabel(); // <-- FALTABA ESTO
        lblMessage = new JLabel(); // <-- También inicializa estos si aún no lo haces
        lblMessageText = new JLabel();
        cmdClose = new JButton();
        panel = new JPanel();
        lblIcon.setHorizontalAlignment(SwingConstants.CENTER);
        lblIcon.setIcon(new ImageIcon(ComponentFactory.ruta("success")));

        panel.setOpaque(false);

        lblMessage.setFont(new Font("sansserif", Font.BOLD, 14));
        lblMessage.setForeground(new Color(38, 38, 38));
        lblMessage.setText("Message");

        lblMessageText.setForeground(new Color(127, 127, 127));
        lblMessageText.setText("Message Text");

        GroupLayout panelLayout = new GroupLayout(panel);
        panel.setLayout(panelLayout);
        panelLayout.setHorizontalGroup(
                panelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(panelLayout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(panelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                        .addComponent(lblMessage)
                                        .addComponent(lblMessageText))
                                .addContainerGap(217, Short.MAX_VALUE))
        );
        panelLayout.setVerticalGroup(
                panelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(panelLayout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(lblMessage)
                                .addGap(3)
                                .addComponent(lblMessageText)
                                .addContainerGap())
        );

        cmdClose.setIcon(new ImageIcon(ComponentFactory.ruta("close")));
        cmdClose.setBorder(null);
        cmdClose.setContentAreaFilled(false);
        cmdClose.setFocusable(false);
        cmdClose.addActionListener(this::cmdCloseActionPerformed);

        GroupLayout layout = new GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addGap(20)
                                .addComponent(lblIcon)
                                .addGap(10)
                                .addComponent(panel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(cmdClose)
                                .addGap(15))
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addGap(10)
                                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                        .addComponent(cmdClose)
                                        .addComponent(panel)
                                        .addComponent(lblIcon))
                                .addGap(10))
        );
    }
    private void cmdCloseActionPerformed(ActionEvent evt) {
        closeNotification();
    }
    public enum Type {
        EXITO,
        INFORMACION,
        ADVERTENCIA
    }
    public enum Location {
        TOP_CENTER,
        TOP_RIGHT,
        TOP_LEFT,
        BOTTOM_CENTER,
        BOTTOM_RIGHT, BOTTOM_LEFT,
        CENTER
    }
}
