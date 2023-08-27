(ns info.setmy.validation
    "Command line parameters and values validation functionality."
    (:gen-class)
    (:require [clojure.java.io :as io]))


(defn validate-arguments
    "Validate that path is passed and is directory.

    **Parameters:**

    * **args** (string): Command-line arguments."
    [args]
    (if (seq args)
        (let [file-path (first args)]
            (if (not (nil? file-path))
                (do (println "file-path:" file-path)
                    (if (.isDirectory (io/file file-path))
                        (do (println "Directory passed and is directory: " file-path) true)
                        (do (println "Invalid input") false)))))))
