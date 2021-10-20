(ns jeopardy.view.events
  (:require
    [re-frame.core :as re-frame]))


(re-frame/reg-event-db
  :initialize-db
  (fn [_]
    {:clicks 0}))

(re-frame/reg-event-db
  :button-clicked
  (fn [db] (update db :clicks inc)))