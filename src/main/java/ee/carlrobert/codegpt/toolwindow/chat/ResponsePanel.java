package ee.carlrobert.codegpt.toolwindow.chat;

import static ee.carlrobert.codegpt.util.ThemeUtils.getPanelBackgroundColor;

import com.intellij.icons.AllIcons.Actions;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.ui.JBColor;
import com.intellij.ui.components.JBLabel;
import com.intellij.util.ui.JBFont;
import com.intellij.util.ui.JBUI;
import ee.carlrobert.codegpt.Icons;
import ee.carlrobert.codegpt.toolwindow.IconActionButton;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class ResponsePanel extends JPanel {

  private final Header header;
  private final Body body;

  public ResponsePanel() {
    super(new BorderLayout());
    header = new Header();
    body = new Body();
    add(header, BorderLayout.NORTH);
    add(body, BorderLayout.SOUTH);
  }

  public void enableActions() {
    header.enableActions(true);
  }

  public ResponsePanel withReloadAction(Runnable onReload) {
    header.addReloadAction(onReload);
    return this;
  }

  public ResponsePanel withDeleteAction(Runnable onDelete) {
    header.addDeleteAction(onDelete);
    return this;
  }

  public ResponsePanel addContent(JComponent content) {
    body.addContent(content);
    return this;
  }

  public void updateContent(JComponent content) {
    body.updateContent(content);
  }

  public JComponent getContent() {
    return body.getContent();
  }

  static class Header extends JPanel {

    private final JPanel iconsWrapper;

    Header() {
      super(new BorderLayout());
      setBackground(getPanelBackgroundColor());
      setBorder(JBUI.Borders.empty(12, 8, 4, 8));
      add(getIconLabel(), BorderLayout.LINE_START);

      iconsWrapper = new JPanel(new FlowLayout(FlowLayout.LEFT, 8, 0));
      iconsWrapper.setBackground(getBackground());
      add(iconsWrapper, BorderLayout.LINE_END);
    }

    public void enableActions(boolean enabled) {
      for (var iconButton : iconsWrapper.getComponents()) {
        iconButton.setEnabled(enabled);
      }
    }

    public void addReloadAction(Runnable onReload) {
      iconsWrapper.add(new IconActionButton("Reload response", Actions.Refresh, new AnAction() {
        @Override
        public void actionPerformed(@NotNull AnActionEvent e) {
          enableActions(false);
          onReload.run();
        }
      }));
    }

    public void addDeleteAction(Runnable onDelete) {
      iconsWrapper.add(new IconActionButton("Delete response", Actions.GC, new AnAction() {
        @Override
        public void actionPerformed(@NotNull AnActionEvent e) {
          onDelete.run();
        }
      }));
    }

    private JBLabel getIconLabel() {
      return new JBLabel("CodeGPT", Icons.DefaultIcon, SwingConstants.LEADING)
          .setAllowAutoWrapping(true)
          .withFont(JBFont.label().asBold());
    }
  }

  static class Body extends JPanel {

    private @Nullable JComponent content;

    Body() {
      super(new BorderLayout());
      setBackground(getPanelBackgroundColor());
      setBorder(JBUI.Borders.compound(
          JBUI.Borders.customLine(JBColor.border(), 0, 0, 1, 0),
          JBUI.Borders.empty(4, 8, 8, 8)));
    }

    public void addContent(JComponent content) {
      this.content = content;
      add(content);
    }

    public void updateContent(JComponent content) {
      removeAll();
      revalidate();
      repaint();
      addContent(content);
    }

    public @Nullable JComponent getContent() {
      return content;
    }
  }
}
