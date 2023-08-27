(ns info.setmy.traversal
    "Functionality for recursive file traversal with applying funtion to all files."
    (:gen-class)
    (:require [clojure.java.io :as io]
              [info.setmy.validation :as validation]))

(defn extract-extension
    "Extracts file extension from file object.

    **Parameters:**

    * **file** (java.io.File): A file.
    "
    [file]
    (let [file-name         (.getName file)
          last-dot-position (.lastIndexOf file-name ".")]
        (if (and (> last-dot-position 0) (< last-dot-position (- (count file-name) 1)))
            (subs file-name (inc last-dot-position))
            nil)))

(defn ^:private extract-file-info
    "Extracts file information from a java.io.File object.

    **Parameters:**

    * **file** (java.io.File): A file or directory.

    **Returns:**

     * A map containing file information."
    [file]
    (let [name           (.getName file)
          full-path      (.getAbsolutePath file)
          file-length    (.length file)
          file-extension (extract-extension file)]
        {:file           file
         :name           name
         :full-path      full-path
         :file-length    file-length
         :file-extension file-extension}))

(defn ^:private process-file
    "Processes a file using the provided function [Example](example.com).

    **Parameters:**

    * **file** (java.io.File): The file to process.
    * **fn** (function): A function that takes file information map as argument."
    [file fn]
    (let [file-info (extract-file-info file)]
        (fn file-info)))

(defn traverse-files
    "Recursively traverses files and directories, applying the provided function.

    **Parameters:**

    * **dir-or-file** (java.io.File): The directory or file to start traversal from.
    * **fn** (function): A function to apply to each file with **file-info** map as function parameter."
    [dir-or-file fn]
    (doseq [file (.listFiles dir-or-file)]
        (if (.isDirectory file)
            (traverse-files file fn)
            (process-file file fn))))

(defn example-file-processor
    "Prints file information from the provided file information map.

    **Parameters:**

    * **file-info** (map): A map containing file information."
    [file-info]
    (println "Full path:" (:full-path file-info))
    (println "Name:" (:name file-info))
    (println "Length:" (:file-length file-info))
    (println "Extension:" (:file-extension file-info)))

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
