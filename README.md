# hello-world

FIXME: Write a one-line description of your library/project.

## Overview

FIXME: Write a paragraph about the library/project and highlight its goals.

## Development

### Comand-line
To get an interactive development environment run:

    clojure -M:fig:build

This will auto compile and send all changes to the browser without the
need to reload. After the compilation process is complete, you will
get a Browser Connected REPL. An easy way to try it is:

    (js/alert "Am I connected?")

and you should see an alert in the browser window.

To clean all compiled files:

    rm -rf target/public

To create a production build run:

	rm -rf target/public
	clojure -M:fig:min

### IntelliJ Cursive REPL
To start the REPL within IntelliJ (inspired by https://figwheel.org/docs/cursive.html)
- Create a new run configuration (Select Clojure REPL -> Local)
- Select clojure.main
- Select "Run with deps" and in field Aliases type `fig`
- In parameters, add `dev/user.clj`


## License

Copyright © 2018 FIXME

Distributed under the Eclipse Public License either version 1.0 or (at your option) any later version.
