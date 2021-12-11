import Foundation

public class NormalClock: Clock {
    public var now: TimeInterval {
        Date().timeIntervalSince1970
    }
}
