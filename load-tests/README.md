# Load Testing using Gatling

A game of Rock Paper Scissors is played by 2 participants.\
Things a participant would do with the Rock Paper Scissors service:\
A participant registers with a name.\
The participant creates a session. \
The participant joins a session.\
The participant makes a move.\
The participant checks the result of the game.\

##Tips about writing scenarios
- Do not try to “optimize imports” with your IDE, you’d break everything. Just copy paste those imports wherever you want to use Gatling DSL.
- Refer this for [Simulation Structure](https://gatling.io/docs/gatling/reference/current/general/simulation_structure/)