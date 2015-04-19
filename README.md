# dirz

Eventually a language-agnostic templating thing. Right now, just some
ini-parsing in Clojure.

## Usage

CLI stuff TK. Currently, `dirz.parsing` contains an instaparse parser and some
helper funcs to convert ini files to a vector of maps:    

    // my-ini.ini    

    [section1]    foo=bar    bar=baz    [section2]    what=this    who=me    

    
    // becomes    

    [{:Section "section1" "foo" "bar" "bar" "baz"} {:Section "section2" "what"
    "this" "who" "me"}]

## License

Copyright © 2015 Sam Raker & Coleman McFarland

Distributed under the Eclipse Public License either version 1.0 or (at
your option) any later version.
