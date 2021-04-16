package org.myapp

//import cats.Foldable

trait UserStorage[F[_], UserId] {
  val heap: F[User]

  def getEvent(id: UserId): User = ???

  def addUser(id: UserId, user: User): Unit = ???

  def redactUser(id: UserId, user: User): Unit = ???
}
