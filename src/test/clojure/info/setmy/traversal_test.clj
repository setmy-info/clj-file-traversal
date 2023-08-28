(ns info.setmy.traversal-test
    (:require [clojure.test :refer :all]
              [clojure.string :as string]
              [info.setmy.traversal :refer :all]
              [info.setmy.validation :as validation]
              [clojure.java.io :as io]
              [clojure.pprint :refer [pprint]]))

(deftest a-test
    (testing "FIXME, I fail."
             (is (= 1 1))))

(defn normalize-path [path]
    (string/replace path (java.io.File/separator) "/"))

(defn ends-with-os-path? [string suffix]
    (string/ends-with? (normalize-path string) suffix))

(deftest test-traverse-files
    (let [root-path "./src/test/resources/root"]
        (let [visited-files (atom [])]
            (traverse-files (io/file root-path)
                            (fn [file-info]
                                (swap! visited-files conj file-info)))

            (testing "Traversing files"
                     (let [file-map (nth @visited-files 0)]
                         (is (= (:name file-map) ".no_extension_file"))
                         (is (ends-with-os-path? (:full-path file-map) "/root/.no_extension_file"))
                         (is (= (.getAbsolutePath (:file file-map)) (:full-path file-map)))
                         (is (= (:length file-map) 9))
                         (is (nil? (:extension file-map))))
                     (let [file-map (nth @visited-files 1)]
                         (is (= (:name file-map) "abc.longext"))
                         (is (ends-with-os-path? (:full-path file-map) "/root/abc.longext"))
                         (is (= (.getAbsolutePath (:file file-map)) (:full-path file-map)))
                         (is (= (:length file-map) 12))
                         (is (= (:extension file-map) "longext")))
                     (let [file-map (nth @visited-files 2)]
                         (is (= (:name file-map) "dir1.txt"))
                         (is (ends-with-os-path? (:full-path file-map) "/root/dir1/dir1.txt"))
                         (is (= (.getAbsolutePath (:file file-map)) (:full-path file-map)))
                         (is (= (:length file-map) 29))
                         (is (= (:extension file-map) "txt")))
                     (let [file-map (nth @visited-files 3)]
                         (is (= (:name file-map) "sub1.txt"))
                         (is (ends-with-os-path? (:full-path file-map) "/root/dir1/sub1/sub1.txt"))
                         (is (= (.getAbsolutePath (:file file-map)) (:full-path file-map)))
                         (is (= (:length file-map) 46))
                         (is (= (:extension file-map) "txt")))
                     (let [file-map (nth @visited-files 4)]
                         (is (= (:name file-map) "dir2.txt"))
                         (is (ends-with-os-path? (:full-path file-map) "/root/dir2/dir2.txt"))
                         (is (= (.getAbsolutePath (:file file-map)) (:full-path file-map)))
                         (is (= (:length file-map) 34))
                         (is (= (:extension file-map) "txt")))
                     (let [file-map (nth @visited-files 5)]
                         (is (= (:name file-map) "sub2.txt"))
                         (is (ends-with-os-path? (:full-path file-map) "/root/dir2/sub2/sub2.txt"))
                         (is (= (.getAbsolutePath (:file file-map)) (:full-path file-map)))
                         (is (= (:length file-map) 56))
                         (is (= (:extension file-map) "txt")))
                     (let [file-map (nth @visited-files 6)]
                         (is (= (:name file-map) "no_extension_file"))
                         (is (ends-with-os-path? (:full-path file-map) "/root/no_extension_file"))
                         (is (= (.getAbsolutePath (:file file-map)) (:full-path file-map)))
                         (is (= (:length file-map) 5))
                         (is (nil? (:extension file-map))))
                     (let [file-map (nth @visited-files 7)]
                         (is (= (:name file-map) "root.txt"))
                         (is (ends-with-os-path? (:full-path file-map) "/root/root.txt"))
                         (is (= (.getAbsolutePath (:file file-map)) (:full-path file-map)))
                         (is (= (:length file-map) 10))
                         (is (= (:extension file-map) "txt"))))

            (println "All files visited.")
            (pprint @visited-files))))

(deftest test-as-main
    (let [root-path "./src/test/resources/root"
          args      [root-path]]
        (if (validation/validate-arguments args)
            (let [file-path (first args)]
                (println "Directory to be recursivelly handled:" file-path)
                (traverse-files (io/file file-path) example-file-processor))
            (println "Directori is not passed"))))
