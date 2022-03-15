class Robot(var x: Int, var y: Int,val fieldSize: Int) {
  
  fun crossBoundary(coord: Int ): Int {
      return when {
          coord > 0 -> coord % fieldSize
      	  else -> (fieldSize - ((-coord) % fieldSize)) % fieldSize
      }
  }

  fun goRight(steps: Int) {
    x += steps
    
    x = crossBoundary(x)
  }
  
  fun goLeft(steps: Int) {
    x -= steps
    x = crossBoundary(x)
  }
  
  fun goUp(steps: Int) {
    y -= steps
    
    y = crossBoundary(y)
  }
  
  fun goDown(steps: Int) {
    y += steps
    
    y = crossBoundary(y)
  }

  fun getLocation(): String {
  	return "Robot(x=$x,y=$y)"
  }
}

fun main() {
  val robot = Robot(0,0,5)
  robot.goUp(10)
  println(robot.getLocation())
  robot.goDown(5)
  println(robot.getLocation())
  robot.goRight(5)
  println(robot.getLocation())
  robot.goLeft(10)
  println(robot.getLocation())
}