import Foundation

struct Account {
    let id: Int
    let name: String
    let age: UInt
}

protocol EventStore {
    func addEvent(_ event: Event, accountId: Int)
    func eventsById(_ accountId: Int) -> [Event]
}

class InMemoryEventStore: EventStore {
    private var events: [Int: [Event]] = [:]
    
    func addEvent(_ event: Event, accountId: Int) {
        if let accountEvents = events[accountId] {
            events[accountId] = accountEvents + [event]
        } else {
            events[accountId] = [event]
        }
    }
    
    func eventsById(_ accountId: Int) -> [Event] {
        events[accountId] ?? []
    }
}
