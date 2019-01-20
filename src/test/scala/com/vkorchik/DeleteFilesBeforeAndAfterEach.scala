package com.vkorchik

import java.io.File

import org.scalatest.BeforeAndAfterEach


trait DeleteFilesBeforeAndAfterEach {
  this: BeforeAndAfterEach =>

  protected val files: Seq[File]

  override protected def beforeEach(): Unit = files.foreach(_.delete())
  override protected def afterEach(): Unit = files.foreach(_.delete())
}
