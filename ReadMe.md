FlightyBot - Scalatron Bot 
 
FlightyBot is an implementation of [Scalatron](http://scalatron.github.com).  

It uses the 'Stackable Trait' pattern to create bots with different intentions:

- RapidExpansion
- SafelyFloat 
- Scout
- Attack
- HeadHome

Each turn a bot decides whether the appropriate conditions have been met to change its intention.  It then carries out the best choice to fulfill this by making a choice for moving, detonating and spawning.

With the exception of a single 'WelcomeRequestRecord' instance, there are no mutable objects used.  The state of each bot is not stored beyond the duration of it's turn to make a move.