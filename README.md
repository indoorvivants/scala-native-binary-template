# Scala Native binary release template

<!--toc:start-->
- [Scala Native binary release template](#scala-native-binary-release-template)
  - [Building](#building)
  - [Distribution](#distribution)
  - [Project structure](#project-structure)
  - [Debugging in VS Code or Zed](#debugging-in-vs-code-or-zed)
<!--toc:end-->

This is a simple template demonstrating building a [Scala Native](https://scala-native.org/) binary and releasing it 
on Github release for all platforms supported by GithubActions: 
- Windows (amd64), 
- Linux (amd64, arm64), 
- MacOS (arm64, amd64)

Binaries are given descriptive platform-specific names, and upon creating a Github release 
for a tag starting with `v`, binaries for all platforms are uploaded to release artifacts.

## Building

SBT Commands to build the binary:

  - `buildBinaryDebug` – produces `./out/debug/app`
  - `buildBinaryRelease` – produces `./out/release/app`
  - `buildBinaryPlatformDebug` - produces `./out/debug/app-...` with platform specific information encoded in the name (e.g. `./out/debug/app-aarch64-apple-darwin`)
  - `buildBinaryPlatformRelease` - produces `./out/release/app-...` with platform specific information encoded in the name (e.g. `./out/release/app-aarch64-apple-darwin`)


Github workflows:

- Main branch merges and PRs: CI ([ci.yml](./.github/workflows/ci.yml)) – just makes sure that `buildBinaryDebug` succeeds on all platforms
- Tags: release ([release.yml](./.github/workflows/release.yml)) – builds platform specific binary using `buildBinaryPlatformRelease` and uploads all
  binaries as Github release assets

## Distribution

Having your Scala Native app uploaded as assets to Github Releases simplifies distribution:

1. To install apps via Coursier, you can use `prebuiltBinaries`: https://github.com/coursier/apps/blob/main/apps-contrib/resources/sn-bindgen.json#L10-L13
2. To install apps via Homebrew, you can use binary distributions in your formulae: https://github.com/indoorvivants/homebrew-tap/blob/d0fbfb854e120c6095be340baaad534f281b31ef/sn-sizemap.rb#L22-L49

## Project structure

This is a two module Scala 3 project

It has a `lib` and `bin` projects, where `bin` depends on `lib`.

The expectation is that most of the functionality of the binary is exposed as a library,
that can be published to Maven Central, and binary merely provides a CLI interface to 
invoke that functionality.

You are of course free to remove the `lib` entirely.

Naming conventions follow Coursier's practices for pre-built binary URLs.

## Debugging in VS Code or Zed

This template includes very basic setup for debugging with VS Code (using [CodeLLDB extension](https://marketplace.visualstudio.com/items?itemName=vadimcn.vscode-lldb)) and Zed.

You should have a `Debug` configuration available, which will automatically build a binary by invoking `sbtn buildBinaryDebug` command - 
so make sure you have sbtn installed (run `sbt installSbtn`).

Please note that debugging information in Scala Native is very experimental, so don't expect a rich debugging experience you can 
get for LLDB-native languages like C or C++.

Some functionality will work. If you spot bugs, please raise an issue on [Scala Native](https://github.com/scala-native/scala-native/),
with a reproduction and possibly a screenshot from the debugger.

![CleanShot 2025-02-15 at 10 11 15](https://github.com/user-attachments/assets/916c37e3-9298-4992-97bf-e49be805e0b9)
