(ns dirz.parsing
  (:require [instaparse.core :as insta]
            [cheshire.core :refer [generate-string
                                   generate-stream]]
            [clojure.java.io :as io]))

(def ini-parser
  "Instaparse parser for .ini files"
  (insta/parser "<INI> = Section +
                 Section = SectionStart <'\n'> SectionBody <'\n'>*
                 <SectionStart> = <'['> SectionName <']'>
                 <SectionName> = #'\\w+'
                 <SectionBody> = (KVP <'\n'>)+
                 <KVP> = K <'='> V
                 <K> = #'\\w+'
                 <V> = #'\\w+'"))

(defn- merge-sections [ms]
  (reduce merge (map (fn [m] {(keyword (:Section m))
                        (dissoc m :Section)})
                      ms)))
(defn to-map
  "Convert a parsed ini to a clojure map"
  [parsed-ini]
  (merge-sections (map (partial apply hash-map) parsed-ini)))

(defn parse-ini
  "Convert an ini to a map"
  [^String ini]
  (-> ini ini-parser to-map))

(defn load-ini
  "Load an ini file as a map"
  [^String ini-file]
  (-> (slurp ini-file) parse-ini))

(defn ini-to-json
  "Write an ini file to JSON"
  ([^String ini-text]
   (generate-string (parse-ini ini-text) {:pretty true}))
  ([^String ini-text ^String dest-file]
   (generate-stream (parse-ini ini-text)
                    (io/writer dest-file) {:pretty true})))
