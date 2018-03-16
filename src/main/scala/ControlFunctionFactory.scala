import com.partridgetech.ControlFunction

/***
  * Function called by Scalatron application to get data from FlightyBot.
  */

class ControlFunctionFactory {
  def create: (String => String) = String => ControlFunction.respond(String)
}
