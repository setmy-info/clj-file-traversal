(defproject info.setmy/clj-file-traversal "0.1.0"
    :description
    "Clojure module to apply function on every file in file hierarchy."
    :url "https://github.com/setmy-info/clj-file-traversal"
    :license
    {:name "MIT License"
     :url  "https://opensource.org/licenses/MIT"}
    :dependencies [[org.clojure/clojure "1.11.1"]]
    :plugins [[lein-codox "0.10.8"]]
    :main ^:skip-aot info.setmy.traversal
    :target-path "target/%s"
    :source-paths ["src/main/clojure"]
    :test-paths ["src/test/clojure"]
    :resource-paths ["src/main/resources" "./target/classes"]
    :profiles
    {:uberjar {:aot      :all
               :jvm-opts ["-Dclojure.compiler.direct-linking=true"]}
     :local   {}
     :dev     {}
     :ci      {}
     :test    {}
     :prelive {}
     :live    {}}
    :deploy-repositories
    [["clojars"
      {:url           "https://clojars.org/repo"
       :sign-releases false}]]
    :aliases {"doc" ["do" "codox"]}
    :codox
    {:metadata {:doc/format :markdown}})
