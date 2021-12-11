import Foundation

public class SetableClock: Clock {
    private var _now: TimeInterval
    
    public init(_ now: TimeInterval) {
        self._now = now
    }
    
    public var now: TimeInterval {
        get {
            _now
        }
        set {
            _now = newValue
        }
    }
}
