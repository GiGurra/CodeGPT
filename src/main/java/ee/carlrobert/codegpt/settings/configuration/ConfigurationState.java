package ee.carlrobert.codegpt.settings.configuration;

import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.components.PersistentStateComponent;
import com.intellij.openapi.components.State;
import com.intellij.openapi.components.Storage;
import com.intellij.util.xmlb.XmlSerializerUtil;
import ee.carlrobert.codegpt.actions.editor.EditorActionsUtil;
import java.util.Map;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@State(
    name = "ee.carlrobert.codegpt.state.settings.configuration.ConfigurationState",
    storages = @Storage("CodeGPTConfiguration.xml")
)
public class ConfigurationState implements PersistentStateComponent<ConfigurationState> {

  private String systemPrompt = "";
  private int maxTokens = 1000;
  private double temperature = 0.2;
  private boolean createNewChatOnEachAction;
  private Map<String, String> tableData = EditorActionsUtil.DEFAULT_ACTIONS;

  public static ConfigurationState getInstance() {
    return ApplicationManager.getApplication().getService(ConfigurationState.class);
  }

  @Nullable
  @Override
  public ConfigurationState getState() {
    return this;
  }

  @Override
  public void loadState(@NotNull ConfigurationState state) {
    XmlSerializerUtil.copyBean(state, this);
  }

  public String getSystemPrompt() {
    return systemPrompt;
  }

  public void setSystemPrompt(String systemPrompt) {
    this.systemPrompt = systemPrompt;
  }

  public int getMaxTokens() {
    return maxTokens;
  }

  public void setMaxTokens(int maxTokens) {
    this.maxTokens = maxTokens;
  }

  public double getTemperature() {
    return temperature;
  }

  public void setTemperature(double temperature) {
    this.temperature = temperature;
  }

  public boolean isCreateNewChatOnEachAction() {
    return createNewChatOnEachAction;
  }

  public void setCreateNewChatOnEachAction(boolean createNewChatOnEachAction) {
    this.createNewChatOnEachAction = createNewChatOnEachAction;
  }

  public Map<String, String> getTableData() {
    return tableData;
  }

  public void setTableData(Map<String, String> tableData) {
    this.tableData = tableData;
  }
}
