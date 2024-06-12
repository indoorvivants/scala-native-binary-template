# Scala Native binary release template

This is a simple template demonstrating building a [Scala Native](https://scala-native.org/) binary and releasing it 
on Github release for all natively supported platforms: Windows (amd64), Linux (amd64), 
MacOS (arm64), MacOS (amd64).

Binaries are given descriptive platform-specific names, and upon creating a Github release 
for a tag starting with `v`, binaries for all platforms are uploaded to release artifacts.

The project itself

- Uses latest Scala 3
- Has a `lib` and `bin` projects, where `bin` depends on `lib`

  The expectation is that most of the functionality of the binary is exposed as a library,
  that can be published to Maven Central, and binary merely provides a CLI interface to 
  invoke that functionality.

  You are of course free to remove the `lib` entirely.

Naming conventions loosely follow Coursier's practices.
