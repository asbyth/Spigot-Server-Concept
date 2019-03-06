package net.ethereal.ethereal.config;

import java.io.File;

public class FileHandler {

  /**
   * setup the file directory
   * got all the permissions shit from a tutorial
   */
  public void setup() {
    String etherealDirectory = "plugins/ethereal";
    File pluginDirectory = new File(etherealDirectory);

    if (!pluginDirectory.exists()) {
      pluginDirectory.mkdir();
    }

    File data = new File(etherealDirectory + "/userdata");

    if (!data.exists()) {
      data.mkdir();
    }
  }
}
