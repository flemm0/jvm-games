package org.jvmgames.core

trait Game:
  def name: String
  def description: String
  def run(): Unit
