package org.myapp

//import cats.Foldable

trait UserStorage[F[_], UserId] {

  def getEvent(id: UserId): User

  def addUser(id: UserId, user: User): Unit

  def redactUser(id: UserId, user: User): Unit
}
