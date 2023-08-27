(ns info.setmy.traversal-test
    (:require [clojure.test :refer :all]
              [clojure.string :as string]
              [info.setmy.traversal :refer :all]
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
                         (is (= (:name file-map) "dir1.txt"))
                         (is (ends-with-os-path? (:full-path file-map) "/root/dir1/dir1.txt"))
                         (is (= (.getAbsolutePath (:file file-map)) (:full-path file-map)))
                         (is (= (:length file-map) 29)))
                     (let [file-map (nth @visited-files 1)]
                         (is (= (:name file-map) "sub1.txt"))
                         (is (ends-with-os-path? (:full-path file-map) "/root/dir1/sub1/sub1.txt"))
                         (is (= (.getAbsolutePath (:file file-map)) (:full-path file-map)))
                         (is (= (:length file-map) 46)))
                     (let [file-map (nth @visited-files 2)]
                         (is (= (:name file-map) "dir2.txt"))
                         (is (ends-with-os-path? (:full-path file-map) "/root/dir2/dir2.txt"))
                         (is (= (.getAbsolutePath (:file file-map)) (:full-path file-map)))
                         (is (= (:length file-map) 34)))
                     (let [file-map (nth @visited-files 3)]
                         (is (= (:name file-map) "sub2.txt"))
                         (is (ends-with-os-path? (:full-path file-map) "/root/dir2/sub2/sub2.txt"))
                         (is (= (.getAbsolutePath (:file file-map)) (:full-path file-map)))
                         (is (= (:length file-map) 56)))
                     (let [file-map (nth @visited-files 4)]
                         (is (= (:name file-map) "root.txt"))
                         (is (ends-with-os-path? (:full-path file-map) "/root/root.txt"))
                         (is (= (.getAbsolutePath (:file file-map)) (:full-path file-map)))
                         (is (= (:length file-map) 10))))

            (println "All files visited.")
            (pprint @visited-files))))