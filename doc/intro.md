# Introduction to clj-file-traversal

Applies function on all files in file system structure in file sub folders

Example main:

```clojure
(ns your.namespace.file-traversal
    (:require [info.setmy.traversal :refer :all]
              [info.setmy.validation :as validation]
              [clojure.java.io :as io]))


(defn -main
    "Entry point of the application. Traverses files starting from the specified root path.
  
    **Parameters:**
  
    * **args** (string): Command-line arguments."
    [& args]
    (if (validation/validate-arguments args)
        (let [file-path (first args)]
            (println "Directory to be recursivelly handled:" file-path)
            (traverse-files (io/file file-path) example-file-processor))
        (println "Directori is not passed")))
```

Example traversal callback function:

```clojure
(defn example-file-processor
    "Prints file information from the provided file information map.
    
        **Parameters:**
    
        * **file-info** (map): A map containing file information."
    [file-info]
    (println "Full path:" (:full-path file-info))
    (println "Name:" (:name file-info))
    (println "Length:" (:length file-info))
    (println "Extension:" (:extension file-info))
    (println "Last modify date:" (:last-modified-date file-info)))
```
