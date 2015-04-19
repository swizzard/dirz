(ns dirz.parsing
  (:require [instaparse.core :as insta]))

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

(defn to-map
  "Convert a parsed ini to a clojure map"
  [parsed-ini]
  (map (partial apply hash-map) parsed-init))

(defn parse-ini
  "Convert an ini to a map"
  [^String ini]
  (-> ini ini-parser to-map))

(defn load-ini
  "Load an ini file as a map"
  [^String ini-file]
  (-> (slurp ini-file) parse-ini))
