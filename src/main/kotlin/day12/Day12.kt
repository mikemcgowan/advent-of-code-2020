package day12

import java.io.File
import kotlin.math.abs

enum class Direction {
    NORTH, EAST, SOUTH, WEST
}

data class Ship(val facing: Direction, val x: Int, val y: Int)
data class Waypoint(val dx: Int, val dy: Int)
data class ShipWithWaypoint(val x: Int, val y: Int, val waypoint: Waypoint)

fun main() {
    val input = File("src/main/kotlin/day12/input.txt").readLines()
    val result1 = part1(input)
    println(result1)
    val result2 = part2(input)
    println(result2)
}

fun part1(input: List<String>): Int {
    val ship = input.fold(Ship(Direction.EAST, 0, 0)) { acc, command ->
        val cmd = command[0]
        val amt = command.substring(1).toInt()
        when (cmd) {
            'N' -> Ship(acc.facing, acc.x, acc.y - amt)
            'E' -> Ship(acc.facing, acc.x + amt, acc.y)
            'S' -> Ship(acc.facing, acc.x, acc.y + amt)
            'W' -> Ship(acc.facing, acc.x - amt, acc.y)
            'R' -> Ship(rotateRight(acc.facing, amt), acc.x, acc.y)
            'L' -> Ship(rotateLeft(acc.facing, amt), acc.x, acc.y)
            'F' -> goForward(acc, amt)
            else -> acc
        }
    }
    return abs(ship.x) + abs(ship.y)
}

fun part2(input: List<String>): Int {
    val ship = input.fold(ShipWithWaypoint(0, 0, Waypoint(10, 1))) { acc, command ->
        val cmd = command[0]
        val amt = command.substring(1).toInt()
        when (cmd) {
            'N' -> ShipWithWaypoint(acc.x, acc.y, Waypoint(acc.waypoint.dx, acc.waypoint.dy + amt))
            'E' -> ShipWithWaypoint(acc.x, acc.y, Waypoint(acc.waypoint.dx + amt, acc.waypoint.dy))
            'S' -> ShipWithWaypoint(acc.x, acc.y, Waypoint(acc.waypoint.dx, acc.waypoint.dy - amt))
            'W' -> ShipWithWaypoint(acc.x, acc.y, Waypoint(acc.waypoint.dx - amt, acc.waypoint.dy))
            'R' -> ShipWithWaypoint(acc.x, acc.y, rotateWaypointRight(acc, amt))
            'L' -> ShipWithWaypoint(acc.x, acc.y, rotateWaypointLeft(acc, amt))
            'F' -> goToWaypoint(acc, amt)
            else -> acc
        }
    }
    return abs(ship.x) + abs(ship.y)
}

fun rotateRight(facing: Direction, degrees: Int): Direction =
    if (degrees <= 0) facing else
        rotateRight(
            when (facing) {
                Direction.NORTH -> Direction.EAST
                Direction.EAST -> Direction.SOUTH
                Direction.SOUTH -> Direction.WEST
                Direction.WEST -> Direction.NORTH
            }, degrees - 90
        )

fun rotateLeft(facing: Direction, degrees: Int): Direction =
    if (degrees <= 0) facing else
        rotateLeft(
            when (facing) {
                Direction.NORTH -> Direction.WEST
                Direction.WEST -> Direction.SOUTH
                Direction.SOUTH -> Direction.EAST
                Direction.EAST -> Direction.NORTH
            }, degrees - 90
        )

fun goForward(ship: Ship, amt: Int): Ship =
    when (ship.facing) {
        Direction.NORTH -> Ship(ship.facing, ship.x, ship.y - amt)
        Direction.EAST -> Ship(ship.facing, ship.x + amt, ship.y)
        Direction.SOUTH -> Ship(ship.facing, ship.x, ship.y + amt)
        Direction.WEST -> Ship(ship.facing, ship.x - amt, ship.y)
    }

fun rotateWaypointRight(ship: ShipWithWaypoint, degrees: Int): Waypoint =
    if (degrees <= 0) ship.waypoint else
        rotateWaypointRight(
            ShipWithWaypoint(ship.x, ship.y, Waypoint(ship.waypoint.dy, -ship.waypoint.dx)),
            degrees - 90
        )

fun rotateWaypointLeft(ship: ShipWithWaypoint, degrees: Int): Waypoint =
    if (degrees <= 0) ship.waypoint else
        rotateWaypointLeft(
            ShipWithWaypoint(ship.x, ship.y, Waypoint(-ship.waypoint.dy, ship.waypoint.dx)),
            degrees - 90
        )

fun goToWaypoint(ship: ShipWithWaypoint, times: Int): ShipWithWaypoint =
    if (times <= 0) ship else
        goToWaypoint(
            ShipWithWaypoint(ship.x + ship.waypoint.dx, ship.y + ship.waypoint.dy, ship.waypoint),
            times - 1
        )
