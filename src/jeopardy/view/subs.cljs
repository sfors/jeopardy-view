(ns jeopardy.view.subs
  (:require [re-frame.core :as re-frame]))

(re-frame/reg-sub
  ::clicks
  (fn [db] (:clicks db)))

