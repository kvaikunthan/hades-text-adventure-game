public class Item {

  private String name;
  private int weight;
  private String desc;

  
  public Item(String name, int weight, String desc) {
    this.name = name;
    this.weight = weight;
    this.desc = desc;
  }

  /**
   * @return name instance variable
   */
  public String getName() {
    return name;
  }

  /**
   * @return name Name's first word, so it can be read by Command
   */
  public String getReadName() {
    return name.substring(0, name.indexOf(" "));
  }

  public int getWeight() {
    return weight;
  }

  public String getDesc() {
    return desc;
  }

}