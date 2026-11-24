# Air Freeze — Fabric 1.21.4

Client-side prototype for Minecraft Java 1.21.4.

## Features
- Press **H** to toggle the best-effort movement freeze.
- The mod zeros player velocity while enabled.
- It does **not** pin the player to saved coordinates. This is intentional: a normal Ender Pearl teleport accepted by the server should not be undone by snapping the player back to the old position.
- Ender Pearls are not modified; there is no automatic upward movement.

## Multiplayer limitations
This is client-side only. The server remains authoritative over movement, gravity, collisions, and Ender Pearl teleportation. A server may still move the player, correct the position, or reject movement. This prototype cannot force a player into solid blocks or guarantee that a pearl will place the player inside a wall. The freeze is best-effort and has not been verified on a multiplayer server.

## Build
Requires JDK 21 and Gradle.

```bash
gradle build
```

The JAR will be in `build/libs/`.

## Install
Put the built JAR in the client's `mods` folder together with Fabric API for Minecraft 1.21.4.

## Keybind
Default: **H**. It can be changed in Options → Controls → Key Binds.
