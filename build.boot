(set-env! :dependencies '[[boot/core                 "2.0.0-pre27" :scope "provided"]
                          [tailrecursion/boot-useful "0.1.3"       :scope "test"] 
                          [adzerk/boot-cljs "0.0-2371-27"          :scope "test"]
                          [adzerk/boot-cljs-repl "0.1.6" :scope "test"]])
(require
 '[tailrecursion.boot-useful :refer :all]
 '[adzerk.boot-cljs :refer :all]
 '[adzerk.boot-cljs-repl :refer :all])

(def +version+ "0.0.1")

(useful! +version+)

(task-options!
  pom  [:project     'boot-sassc
        :version     +version+
        :description "a small, simple HTTP server library for Node.js written in ClojureScript"
        :url         "https://github.com/mathias/elderberry"
        :scm         {:url "https://github.com/mathias/elderberry"}
        :license     {:name "Eclipse Public License"
                      :url  "http://www.eclipse.org/legal/epl-v10.html"}])

(deftask node-compile
  "Settings for node.js compilation"
  []
  (cljs :node-target true
        :optimizations :simple
        :pretty-print true))

(deftask compile-example-app
  "Compile the example Hello World app for node."
  [])

(deftask watch-compile
  "Generate JS from cljs"
  []
  (comp (watch) (node-compile)))
