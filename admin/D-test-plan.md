
# Test plan

## List of classes


#### **1. Class: Marrakech**

**Methods Tested in Isolation:**
- `setGameInfo(String gameString)` - (for Terry)

**Conditions Tested Beyond Isolation:**
-  //TODO

  **Example Test**:
   ```java
   Marrakech game = new Marrakech();
   game.setGameInfo("validGameStringHere");
   assertEquals("validGameStringHere", game.getGameState());
   ```

#### **2. Class: Board**

**Methods Tested in Isolation:**
- `getBoardState()`
- `setTile(String abbreviatedRugState, int row, int col)` (for Gennie)
- `getTile(int x, int y)`

**Conditions Tested Beyond Isolation:**
-  //TODO

#### **3. Class: Assam**

**Methods Tested in Isolation:**
- `getAssamState()`
- `setAssamState(String assamString)`

**Conditions Tested Beyond Isolation:**
- After setting Assam's state using `setAssamState`, calling `getAssamState` should return the same state.

  **Example Test**:
   ```java
   Assam a = new Assam();
   a.setAssamState("validAssamStateHere");
   assertEquals("validAssamStateHere", a.getAssamState());
   ```

#### **4. Class: Player**

**Methods Tested in Isolation:**
- `getPlayerState()`
- `addDirhams(int amountOfDirhams)`
- `subDirhams(int amountOfDirhams)`
- `subPlayerRug(int amountOfRugs)` (for Morris)

**Conditions Tested Beyond Isolation:**
- Setting a player's state using `setPlayerState` should mean that `getPlayerState` retrieves the same state.

  **Example Test**:
   ```java
   Player p = new Player();
   p.setPlayerState("validPlayerStateHere");
   assertEquals("validPlayerStateHere", p.getPlayerState());
   ```

#### **5. Class: Rug**

**Methods Tested in Isolation:**
- `getRugState()`
- `setRugState(String rugString)`

**Conditions Tested Beyond Isolation:**
- After setting a rug's state using `setRugState`, calling `getRugState` should retrieve the same rug state.

  **Example Test**:
   ```java
   Rug r = new Rug();
   r.setRugState("validRugStateHere");
   assertEquals("validRugStateHere", r.getRugState());
   ```

---
