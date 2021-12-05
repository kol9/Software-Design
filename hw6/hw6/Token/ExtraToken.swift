//
//  ExtraToken.swift
//  hw6
//
//  Created by Nikolay Yarlychenko on 05.12.2021.
//

import Foundation

public enum ExtraToken: String, ArithToken {
    public func accept(_ visitor: TokenVisitor) {
        visitor.visit(self)
    }
    
    case leftPar = "("
    case rightPar = ")"
}
