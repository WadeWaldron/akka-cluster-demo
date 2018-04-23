package clusterdemo

object Boot extends App with ConfigOverrides {
  applyConfigOverrides(args)

  val module = new Module

  module.startup()

  sys.addShutdownHook {
    println("SHUTTING DOWN")
    module.shutdown()
  }
}
