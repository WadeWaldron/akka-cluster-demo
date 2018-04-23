package clusterdemo

import java.net.{Inet4Address, NetworkInterface}

import scala.collection.convert.Wrappers.JEnumerationWrapper

trait HostName {
  protected val hostname: String = {
    Option(NetworkInterface.getByName("enp0s8")).flatMap { interface =>
      val addresses = JEnumerationWrapper(interface.getInetAddresses)

      addresses.find {
        case addr: Inet4Address => true
        case _ => false
      }
    }.map(_.getHostAddress).getOrElse("127.0.0.1")
  }
}
