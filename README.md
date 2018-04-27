# Java Compiler
This is a java program used to compile Java files, outputting native x86-64 code instead
of .class files.

## Introduction
This program is written in Java, with the use of JavaCC as the parser.
So far, only the syntax of the language is checked.

## Usage
1. ```make```
  - builds the source files
2. ```java JavaCompiler <main source file>```
  - invokes the compiler on the main source file
  - this compiles the file, and all files that it depends on

## Java Features not implemented
You may get really strange error messages if you use any of these.
- Diamond for generics  (ex: <code>ArrayList<String> s = new ArrayList<>()</code>)
- Nested classes
- Annotations (currently treated as comments)
- Lambdas
- Method references

## Current known bugs
- Nested generics (ex: <code>HashMap&lt;String, ArrayList&lt;String&gt;&gt;</code>) have to be written without the <code>&gt;&gt;</code>
  - write instead as <code>HashMap&lt;String, ArrayList&lt;String&gt; &gt;</code>
  - nested generics makes the grammar not context-free, so this might be hard to implement
