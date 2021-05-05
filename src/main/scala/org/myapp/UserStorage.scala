package org.myapp

trait UserStorage[F[_], UserId] {

  def getUser(id: UserId): User

  def addUser(id: UserId, user: User): Unit

  def redactUser(id: UserId, user: User): Unit
}
