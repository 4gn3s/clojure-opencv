(defproject opencv "0.1.0-SNAPSHOT"
  :description "OpenCV playground"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :repositories  {"project" "file:repo"}
  :jvm-opts ["-Djava.library.path=opencv/"]
  :main opencv.core
  :dependencies [[org.clojure/clojure "1.6.0-RC1"]
                 [local/opencv "2.4.6"]])
