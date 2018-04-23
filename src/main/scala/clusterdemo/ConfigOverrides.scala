package clusterdemo

trait ConfigOverrides { self: App =>
  private val Opt = """(\S+)=(\S+)""".r

  protected def argsToOpts(args: Seq[String]): Map[String, String] = {
    args.collect {
      case Opt(key, value) => key -> value
    }.toMap
  }

  protected def applySystemProperties(options: Map[String, String]): Unit = {
    for ((key, value) <- options if key startsWith "-D") {
      println(s"OVERRIDE: $key=$value")
      System.setProperty(key substring 2, value)
    }
  }

  protected def applyConfigOverrides(args: Array[String]): Unit = {
    applySystemProperties(argsToOpts(args))
  }
}
