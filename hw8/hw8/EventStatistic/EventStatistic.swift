import Foundation

fileprivate enum Constants {
    static let minutesPerHour: Int = 60
    static let secondsPerMinute: Int = 60
}

public class EventStatistic: EventStatisticProtocol {
    private var events: [String: [TimeInterval]]
    private var clock: Clock
    
    public init(clock: Clock) {
        self.clock = clock
        self.events = [:]
    }
    
    public func incEvent(name: String) {
        let now = clock.now
        actualizeEventsForLastHour(now: now)
        events[name, default: []].append(now)
    }
    
    public func getEventStatisticBy(name: String) -> Double {
        let now = clock.now
        actualizeEventsForLastHour(now: now)
        guard let eventsByName = events[name] else { return 0 }
        return Double(eventsByName.count) / Double(Constants.minutesPerHour)
    }
    
    public func getAllEventStatistic() -> [String: Double] {
        var rpmByEventName: [String: Double] = [:]
        let now = clock.now
        actualizeEventsForLastHour(now: now)
        events.forEach { (k, v) in
            rpmByEventName[k] = Double(v.count) / Double(Constants.minutesPerHour)
        }
        return rpmByEventName
    }
    
    public func printStatistic() {
        let stats = getAllEventStatistic()
        stats.forEach { (k, v) in
            print("Event \(k), rpm: \(v) times")
        }
    }
    
    // MARK: - Helpers
    
    private func actualizeEventsForLastHour(now: TimeInterval) {
        self.events.keys.forEach {
            self.events[$0] = self.events[$0]?.filter { now.hours(from: $0) < 1 }
        }
    }
}

extension TimeInterval {
    func hours(from timestamp: TimeInterval) -> Int {
        let from = Int(timestamp)
        let to = Int(self)
        guard from < to else { return 0 }
        return (to - from) / Constants.secondsPerMinute / Constants.minutesPerHour
    }
}
