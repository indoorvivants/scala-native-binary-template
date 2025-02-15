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

## Debugging in VS Code

This template includes very basic setup for debugging with VS Code and [CodeLLDB extension](https://marketplace.visualstudio.com/items?itemName=vadimcn.vscode-lldb).

You should have a `Debug` configuration available, which will automatically build a binary by invoking `sbtn buildBinary` command - 
so make sure you have sbtn installed (run `sbt installSbtn`).

Please note that debugging information in Scala Native is very experimental, so don't expect a rich debugging experience you can 
get for LLDB-native languages like C or C++.

Some functionality will work. If you spot bugs, please raise an issue on [Scala Native](https://github.com/scala-native/scala-native/),
with a reproduction and possibly a screenshot from the debugger.

![CleanShot 2025-02-15 at 10 11 15](https://github.com/user-attachments/assets/916c37e3-9298-4992-97bf-e49be805e0b9)
