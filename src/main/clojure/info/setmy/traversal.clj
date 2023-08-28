(ns info.setmy.traversal
    "Functionality for recursive file traversal with applying funtion to all files."
    (:gen-class)
    (:require [clojure.java.io :as io]
              [info.setmy.validation :as validation]))

(defn ^:private extract-extension
    "Extracts file extension from file object.

    **Parameters:**

    * **file** (java.io.File): A file.
    "
    [file]
    (let [file-name         (.getName file)
          last-dot-position (.lastIndexOf file-name ".")]
        (if (and (> last-dot-position 0) (< last-dot-position (- (count file-name) 1)))
            (clojure.string/lower-case (subs file-name (inc last-dot-position)))
            nil)))

(defn ^:private extract-file-info
    "Extracts file information from a java.io.File object.

    **Parameters:**

    * **file** (java.io.File): A file or directory.

    **Returns:**

     * A map containing file information."
    [file]
    (let [name               (.getName file)
          full-path          (.getAbsolutePath file)
          file-length        (.length file)
          file-extension     (extract-extension file)
          last-modified-ms   (.lastModified file)
          last-modified-date (java.util.Date. last-modified-ms)]
        {:file               file
         :name               name
         :full-path          full-path
         :length             file-length
         :extension          file-extension
         :last-modified-ms   last-modified-ms
         :last-modified-date last-modified-date}))

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
    (println "Length:" (:length file-info))
    (println "Extension:" (:extension file-info))
    (println "Last modify date:" (:last-modified-date file-info)))
